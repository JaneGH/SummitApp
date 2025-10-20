package com.example.summitapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.summitapp.data.local.Converters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
@TypeConverters(Converters::class)
data class Product(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("product_id")
    val productId: Int,

    @SerializedName("category_id")
    val categoryId: Int? = null,

    @SerializedName("sub_category_id")
    val subcategoryId: Int? = null,

    @SerializedName("product_name")
    val productName: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("price")
    val price: Double,

    @SerializedName("average_rating")
    val averageRating: Double? = null,

    @SerializedName("is_active")
    val isActive: String? = null,

    @SerializedName("product_image_url")
    val imageUrl: String? = null,

    @SerializedName("images")
    val images: List<Image>? = null,

    @SerializedName("specifications")
    val specifications: List<Specification>? = null,

    @SerializedName("reviews")
    val reviews: List<Review>? = null
)

data class Image(
    @SerializedName("image")
    val image: String,
    @SerializedName("display_order")
    val displayOrder: Int
)

data class Specification(
    @SerializedName("specification_id")
    val specificationId: Int,
    val title: String,
    val specification: String,
    @SerializedName("display_order")
    val displayOrder: Int
)

data class Review(
    @SerializedName("review_id")
    val reviewId: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("full_name")
    val userName: String,
    @SerializedName("review_title")
    val reviewTitle: String,
    val review: String,
    val rating: Int,
    @SerializedName("review_date")
    val reviewDate: String
)
