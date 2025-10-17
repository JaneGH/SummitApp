package com.example.summitapp.data.remote
import com.example.summitapp.data.remote.request.LoginRequest
import com.example.summitapp.data.remote.request.RegisterRequest
import com.example.summitapp.data.remote.response.CategoriesResponse
import com.example.summitapp.data.remote.response.LoginResponse
import com.example.summitapp.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("User/register")
    fun registerUser(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("User/auth")
    fun loginUser(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("Category")
    fun getCategories(
    ): Call<CategoriesResponse>

    companion object {
        fun getInstance(): ApiService {
            return ApiClient.retrofit.create(ApiService::class.java)
        }
    }
}

