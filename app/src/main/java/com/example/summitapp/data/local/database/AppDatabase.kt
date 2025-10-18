package com.example.summitapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommersproject.model.Category
import com.example.summitapp.data.local.dao.CategoryDao
import com.example.summitapp.data.local.dao.ProductDao
import com.example.summitapp.data.local.entity.Product
import com.example.summitapp.data.local.entity.User


@Database(entities = [User::class, Category::class, Product::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {

        abstract fun CategoryDao(): CategoryDao
        abstract fun ProductDao(): ProductDao

        companion object {
            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getInstance(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "summitdatabase"
                    ).allowMainThreadQueries().build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
