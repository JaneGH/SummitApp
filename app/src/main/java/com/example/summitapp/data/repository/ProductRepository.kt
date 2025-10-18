package com.example.summitapp.data.repository

import com.example.summitapp.data.local.entity.Product
import com.example.summitapp.data.remote.ApiService
import com.example.summitapp.data.remote.response.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val apiService: ApiService) {

    fun getProducts(callback: (List<Product>?, String?) -> Unit) {
        apiService.getProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse != null && productResponse.status == 0) {
                        callback(productResponse.products, null)
                    } else {
                        callback(null, "Error: ${productResponse?.message}")
                    }
                } else {
                    callback(null, "Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                callback(null, "Failed to fetch products. Please retry.")
            }
        })
    }
}
