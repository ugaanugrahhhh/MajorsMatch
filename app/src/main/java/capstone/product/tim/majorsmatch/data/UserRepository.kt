package capstone.product.tim.majorsmatch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import capstone.product.tim.majorsmatch.data.paging.StoryPaging
import capstone.product.tim.majorsmatch.data.pref.UserModel
import capstone.product.tim.majorsmatch.data.pref.UserPreference
import capstone.product.tim.majorsmatch.data.retrofit.ApiService
import capstone.product.tim.majorsmatch.response.AddStoryResponse
import capstone.product.tim.majorsmatch.response.ErrorResponse
import capstone.product.tim.majorsmatch.response.ListStoryItem
import capstone.product.tim.majorsmatch.response.RegisterResponse
import capstone.product.tim.majorsmatch.response.StoryResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository<LoginResponse> private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    private val _isLoading = MutableLiveData<Boolean>()

    private val _successMessage = MutableLiveData<String>()
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(name: String, email: String, password: String): ResultState<RegisterResponse> {
        return try {
            val response = apiService.register(name, email, password)
            ResultState.Success(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            ResultState.Error(errorMessage)
        }
    }

    suspend fun postLogin(email: String, password: String): capstone.product.tim.majorsmatch.response.LoginResponse {
        _isLoading.value = true
        return apiService.login(email, password)
    }

    suspend fun getStories(token: String): StoryResponse {
        return apiService.getStories("Bearer $token")
    }

    suspend fun uploadImage(token: String, imageFile: File, description: String): AddStoryResponse {
        val requestBody = description.toRequestBody("EditText/TextView".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )

        return apiService.uploadImage("Bearer $token", multipartBody, requestBody)
    }

    suspend fun getStoryWithLocation(token: String, location: Int = 1): StoryResponse {
        return apiService.getStoriesWithLocation("Bearer $token",location)
    }

    suspend fun getAllStories (token: String,  page: Int = 1, size: Int = 20) : StoryResponse {
        return apiService.getAllStories("Bearer $token",page, size)
    }

    fun getQuote(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(config = PagingConfig(pageSize = 5), pagingSourceFactory = { StoryPaging(
            apiService,"Bearer $token"
        ) }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: UserRepository<Any?>? = null

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository<Any?> =
            instance
                ?: synchronized(this) {
                instance
                    ?: UserRepository(
                        apiService,
                        userPreference
                    )
            }.also { instance = it }
    }
}