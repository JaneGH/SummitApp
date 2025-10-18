package com.example.summitapp.data.repository

import com.example.ecommersproject.model.Category
import com.example.summitapp.data.local.dao.CategoryDao
import com.example.summitapp.data.remote.ApiService

class CategoryRepository
    (private val apiService: ApiService,
     private val categoryDao: CategoryDao) {

     fun getCategories(): List<Category> {
        return try {
            val response = apiService.getCategories().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status == 0) {
                    val categories = body.categories
                     categoryDao.clearAll()
                     categoryDao.insertAll(categories)
                     categoryDao.getAllCategories()
                } else {
                    categoryDao.getAllCategories()
                }
            } else {
                categoryDao.getAllCategories()
            }
        } catch (e: Exception) {
            categoryDao.getAllCategories()
        }
    }
}