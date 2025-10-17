package com.example.ecommersproject.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email_id")
    val emailId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("mobile_no")
    val mobileNo: String,
    @SerializedName("user_id")
    val userId: String
)