package com.example.summitapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id :Long,
    val title : String,
    val imageUrl : String? = null,
)