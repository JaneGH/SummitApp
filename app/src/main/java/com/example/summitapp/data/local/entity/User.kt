package com.example.summitapp.data.local.entity

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fullName: String,
    val mobileNo: String,
    val emailId: String,
    val password: String
)