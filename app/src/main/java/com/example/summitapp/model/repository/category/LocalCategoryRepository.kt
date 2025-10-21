package com.example.summitapp.model.repository.category

import androidx.lifecycle.LiveData
import com.example.summitapp.model.data.Category
import com.example.summitapp.model.local.database.AppDatabase

class LocalCategoryRepository(private val appDatabase: AppDatabase) {
      fun getCategories(): LiveData<List<Category>> = appDatabase.categoryDao().getAllCategories()
      fun saveCategories(categories: List<Category>) {
          appDatabase.categoryDao().clearAll()
          appDatabase.categoryDao().insertAll(categories)
      }
}