package com.example.summitapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.summitapp.SummitApp
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.request.LoginRequest
import com.example.summitapp.model.remote.response.LoginResponse
import com.example.summitapp.model.repository.LoginRepository
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class LoginViewModel(application: Application, private val repository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse?>()
    val loginResult: LiveData<LoginResponse?> = _loginResult

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _error.value = "Please enter email and password"
            return
        }

        _loading.value = true

        val request = LoginRequest(emailId = email, password = password)
        _loading.value = true

        viewModelScope.launch {
            try {
                val result = repository.loginUser(request)
                _loginResult.value = result
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SummitApp)
                val repository = LoginRepository(ApiService.getInstance())
                LoginViewModel(application, repository)
            }
        }
    }
}
