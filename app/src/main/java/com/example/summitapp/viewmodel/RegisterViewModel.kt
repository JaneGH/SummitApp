package com.example.summitapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.summitapp.SummitApp
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.request.RegisterRequest
import com.example.summitapp.model.remote.response.RegisterResponse
import com.example.summitapp.model.repository.RegisterRepository
import java.util.concurrent.Executors

class RegisterViewModel(application: Application, private val repository: RegisterRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse?>()
    val registerResult: LiveData<RegisterResponse?> = _registerResult

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun register(fullName: String, mobile: String, email: String, password: String, confirmPassword: String) {
        if (fullName.isBlank() || mobile.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _error.value = "Please fill all fields"
            return
        }

        if (password != confirmPassword) {
            _error.value = "Passwords do not match"
            return
        }

        _loading.value = true

        val request = RegisterRequest(
            full_name = fullName,
            mobile_no = mobile,
            email_id = email,
            password = password
        )

        Executors.newSingleThreadExecutor().execute {
            repository.registerUser(request, onResult = {
                _loading.postValue(false)
                _registerResult.postValue(it)
            }, onError = {
                _loading.postValue(false)
                _error.postValue(it)
            })
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SummitApp)
                val repository = RegisterRepository(ApiService.getInstance())
                RegisterViewModel(application, repository)
            }
        }
    }
}
