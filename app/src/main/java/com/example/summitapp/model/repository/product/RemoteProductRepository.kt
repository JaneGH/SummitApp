package com.example.summitapp.model.repository.product

import com.example.summitapp.model.data.Product
import com.example.summitapp.model.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import java.util.concurrent.Future

class RemoteProductRepository(private val apiService: ApiService) {

    suspend fun fetchProducts(query: String): List<Product>? {
        return try {
            val response = apiService.getProducts(query)
            if (response.status == 0) response.products else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}
