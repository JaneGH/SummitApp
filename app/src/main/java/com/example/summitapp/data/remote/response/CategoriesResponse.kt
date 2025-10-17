
package com.example.summitapp.data.remote.response
import com.example.summitapp.data.local.entity.Category
import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)