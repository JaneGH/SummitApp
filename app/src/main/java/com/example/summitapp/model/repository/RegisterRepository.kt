package com.example.summitapp.model.repository

import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.request.RegisterRequest
import com.example.summitapp.model.remote.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRepository(private val apiService: ApiService) {

    suspend fun registerUser(request: RegisterRequest): RegisterResponse? {
        return try {
            apiService.registerUser(request)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
