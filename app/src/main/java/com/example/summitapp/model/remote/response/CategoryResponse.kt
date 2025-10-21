package com.example.summitapp.model.remote.response
import com.example.summitapp.model.data.Category
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)