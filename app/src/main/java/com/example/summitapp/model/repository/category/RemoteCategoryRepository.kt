package com.example.summitapp.model.repository.category

import com.example.summitapp.model.data.Category
import com.example.summitapp.model.remote.ApiService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class RemoteCategoryRepository(private val apiService: ApiService) {

    private val executor = Executors.newSingleThreadExecutor()

    fun fetchCategories(): Future<List<Category>?> {
        return executor.submit<List<Category>?> {
            try {
                val response = apiService.getCategories().execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == 0) body.categories else null
                } else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}