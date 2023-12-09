package capstone.product.tim.majorsmatch.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import capstone.product.tim.majorsmatch.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: capstone.product.tim.majorsmatch.data.UserRepository<Any?>) : ViewModel() {

    val errorLiveData: LiveData<String> = repository.errorLiveData

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    private fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
    fun login(email: String, password: String) {
        viewModelScope.launch {
           val hasil = repository.postLogin(email, password)
            if (hasil.error == false) {
                saveSession(UserModel(email,hasil.loginResult!!.token!!, true ))
            }
        }
    }
}
