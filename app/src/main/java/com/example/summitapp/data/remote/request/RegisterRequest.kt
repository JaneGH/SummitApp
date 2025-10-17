package com.example.summitapp.data.remote.request
import com.google.gson.annotations.SerializedName
data class RegisterRequest(
    @SerializedName("full_name")
    val full_name: String,
    @SerializedName("mobile_no")
    val mobile_no: String,
    @SerializedName("email_id")
    val email_id: String,
    @SerializedName("password")
    val password: String
)