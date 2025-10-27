package com.example.summitapp.model.repository.category

import com.example.summitapp.model.data.Category
import com.example.summitapp.model.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import java.util.concurrent.Future

class RemoteCategoryRepository(private val apiService: ApiService) {

    suspend fun fetchCategories(): List<Category>? {
        return try {
            val response = apiService.getCategories()
            if (response.status == 0) response.categories else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}