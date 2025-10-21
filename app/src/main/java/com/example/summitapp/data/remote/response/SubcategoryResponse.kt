package com.example.summitapp.data.remote.response

import com.example.summitapp.data.model.Subcategory
import com.google.gson.annotations.SerializedName

data class SubcategoryResponse(
    val status: Int,
    val message: String,
    @SerializedName("subcategories")
    val subcategories: List<Subcategory>
)