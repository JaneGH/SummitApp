package com.example.summitapp.data.local

import androidx.room.TypeConverter
import com.example.summitapp.data.model.Image
import com.example.summitapp.data.model.Specification
import com.example.summitapp.data.model.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromImageList(images: List<Image>?): String? {
        return gson.toJson(images)
    }

    @TypeConverter
    fun toImageList(data: String?): List<Image>? {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Image>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromSpecificationList(specs: List<Specification>?): String? {
        return gson.toJson(specs)
    }

    @TypeConverter
    fun toSpecificationList(data: String?): List<Specification>? {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Specification>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromReviewList(reviews: List<Review>?): String? {
        return gson.toJson(reviews)
    }

    @TypeConverter
    fun toReviewList(data: String?): List<Review>? {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Review>>() {}.type
        return gson.fromJson(data, listType)
    }
}
