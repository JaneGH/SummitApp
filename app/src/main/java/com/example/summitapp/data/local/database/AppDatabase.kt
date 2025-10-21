package com.example.summitapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.summitapp.data.local.Converters
import com.example.summitapp.data.local.dao.CategoryDao
import com.example.summitapp.data.local.dao.ProductDao
import com.example.summitapp.data.local.dao.SubcategoryDao
import com.example.summitapp.data.model.Product
import com.example.summitapp.data.model.Category
import com.example.summitapp.data.model.Subcategory
import com.example.summitapp.data.model.User


@TypeConverters(Converters::class)
@Database(entities = [User::class, Subcategory::class, Category::class, Product::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {

        abstract fun categoryDao(): CategoryDao
        abstract fun productDao(): ProductDao
        abstract fun subcategoryDao(): SubcategoryDao

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
