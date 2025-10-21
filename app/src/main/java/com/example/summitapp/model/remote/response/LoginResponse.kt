package com.example.summitapp.model.remote.response
import com.example.summitapp.model.data.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("user")
    val user: User
)