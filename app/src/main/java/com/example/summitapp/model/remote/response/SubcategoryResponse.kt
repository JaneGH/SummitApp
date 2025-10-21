package com.example.summitapp.model.remote.response

import com.example.summitapp.model.data.Subcategory
import com.google.gson.annotations.SerializedName

data class SubcategoryResponse(
    val status: Int,
    val message: String,
    @SerializedName("subcategories")
    val subcategories: List<Subcategory>
)