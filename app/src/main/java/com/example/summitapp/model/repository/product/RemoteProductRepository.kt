package com.example.summitapp.model.repository.product

import com.example.summitapp.model.data.Product
import com.example.summitapp.model.remote.ApiService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class RemoteProductRepository(private val apiService: ApiService) {

    private val executor = Executors.newSingleThreadExecutor()

    fun fetchProducts(query: String): Future<List<Product>?> {
        return executor.submit<List<Product>?> {
            try {
                val response = apiService.getProducts(query).execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == 0) body.products else null
                } else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
