package com.example.summitapp.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("email_id")
    val emailId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("mobile_no")
    val mobileNo: String

)