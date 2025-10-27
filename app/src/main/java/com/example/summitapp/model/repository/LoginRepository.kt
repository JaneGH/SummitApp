package com.example.summitapp.model.repository

import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.request.LoginRequest
import com.example.summitapp.model.remote.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val apiService: ApiService) {

    suspend fun loginUser(request: LoginRequest): LoginResponse? {
        return try {
            apiService.loginUser(request)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
