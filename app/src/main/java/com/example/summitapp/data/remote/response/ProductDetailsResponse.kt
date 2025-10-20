package com.example.summitapp.data.remote.response


import com.example.summitapp.data.model.Product
import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("product")
    val product: Product
)
