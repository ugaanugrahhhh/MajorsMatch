package capstone.product.tim.majorsmatch.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import capstone.product.tim.majorsmatch.data.pref.UserModel
import capstone.product.tim.majorsmatch.response.AddStoryResponse
import capstone.product.tim.majorsmatch.response.ListStoryItem
import capstone.product.tim.majorsmatch.response.StoryResponse
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(private val repository: capstone.product.tim.majorsmatch.data.UserRepository<Any?>) : ViewModel() {

    private val _storyResponse = MutableLiveData<StoryResponse>()

    private val _locationResponse = MutableLiveData<StoryResponse>()
    val locationResponse: LiveData<StoryResponse> = _locationResponse

    private val _addStoryResponse = MutableLiveData<AddStoryResponse>()
    val addStoryResponse: LiveData<AddStoryResponse> = _addStoryResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun register(name: String, email: String, password: String): LiveData<*> {
        val responseLiveData = MutableLiveData<Any>()

        viewModelScope.launch {
            try {
                val response = repository.register(name, email, password)
                responseLiveData.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return responseLiveData
    }


    fun getStories(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getStories(token)
                _storyResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadImage(token : String, file: File, description: String) {
        viewModelScope.launch {
            _addStoryResponse.value = repository.uploadImage(token, file, description)

        }
    }
    fun getStoriesWithLocation(token: String, location: Int = 1) {
        viewModelScope.launch {
            _locationResponse.value = repository.getStoryWithLocation(token,location)
        }
    }

    fun getAllStories(token: String, page: Int = 1, size: Int = 20) {
        viewModelScope.launch {
            repository.getAllStories(token, page, size)
        }
    }
   fun quoteData(token: String) : LiveData<PagingData<ListStoryItem>>  {
       return repository.getQuote(token)
   }
}


