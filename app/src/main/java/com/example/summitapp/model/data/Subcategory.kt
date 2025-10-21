package com.example.summitapp.model.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "subcategories")
data class Subcategory(
    @PrimaryKey
    @SerializedName("subcategory_id")
    val subcategoryId: String,

    @SerializedName("subcategory_name")
    val subcategoryName: String,

    @SerializedName("category_id")
    val categoryId: String,

    @SerializedName("subcategory_image_url")
    val subcategoryImageUrl: String,

    @SerializedName("is_active")
    val isActive: String
)
