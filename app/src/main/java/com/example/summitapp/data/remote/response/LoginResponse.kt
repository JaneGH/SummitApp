package com.example.summitapp.data.remote.response
import com.example.summitapp.data.model.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("user")
    val user: User
)