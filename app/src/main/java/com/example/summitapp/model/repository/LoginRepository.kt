package com.example.summitapp.model.repository

import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.request.LoginRequest
import com.example.summitapp.model.remote.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val apiService: ApiService) {

    fun loginUser(
        request: LoginRequest,
        onResult: (LoginResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.loginUser(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    onError("Failed: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onError(t.message ?: "Failed to login")
            }
        })
    }
}
