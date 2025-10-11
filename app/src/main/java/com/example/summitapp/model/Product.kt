package com.example.summitapp.model

data class Product(
    val id : Long,
    val categoryId : Long,
    val title : String,
    val description : String,
    val price : Double,
    val imageUrl : String? = null
)
