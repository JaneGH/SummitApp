
package com.example.summitapp.data.remote.response
import com.example.summitapp.data.model.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)