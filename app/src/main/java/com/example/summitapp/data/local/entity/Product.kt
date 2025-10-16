package com.example.summitapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val categoryId : Long,
    val title : String,
    val description : String,
    val price : Double,
    val imageUrl : String? = null
)