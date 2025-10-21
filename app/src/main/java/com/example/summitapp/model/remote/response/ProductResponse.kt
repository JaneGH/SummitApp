package com.example.summitapp.model.remote.response
import com.example.summitapp.model.data.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)