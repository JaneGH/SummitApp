package com.example.summitapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
data class Product(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("product_id")
    val productId: Int,

    @SerializedName("category_id")
    val categoryId: Int,

    @SerializedName("product_name")
    val productName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("is_active")
    val isActive: String?,

    @SerializedName("image_url")
    val imageUrl: String? = null
)
