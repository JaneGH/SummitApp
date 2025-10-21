package com.example.summitapp.model.repository

import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.request.RegisterRequest
import com.example.summitapp.model.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository(private val apiService: ApiService) {

    fun registerUser(
        request: RegisterRequest,
        onResult: (RegisterResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    onError("Failed: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onError(t.message ?: "Failed to register")
            }
        })
    }
}
