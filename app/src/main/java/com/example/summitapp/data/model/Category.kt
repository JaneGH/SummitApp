package com.example.summitapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "category")
data class Category(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("category_id")
    val categoryId: Int,

    @SerializedName("localId")
    val localId: Int,

    @SerializedName("category_image_url")
    val categoryImageUrl: String? = null,

    @SerializedName("category_name")
    val categoryName: String,

    @SerializedName("is_active")
    val isActive: String?
)
