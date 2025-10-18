package com.example.summitapp.data.repository

import com.example.summitapp.data.local.dao.ProductDao
import com.example.summitapp.data.model.Product
import com.example.summitapp.data.remote.ApiService

class ProductRepository(
    private val apiService: ApiService,
    private val productDao: ProductDao
) {

    fun getProducts(q:String): List<Product> {
        return try {
            val response = apiService.getProducts(q).execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status == 0) {
                    val products = body.products
                    productDao.clearAll()
                    productDao.insertAllProducts(products)
                    productDao.getAllProducts()
                } else {
                    productDao.getAllProducts()
                }
            } else {
                productDao.getAllProducts()
            }
        } catch (e: Exception) {
            productDao.getAllProducts()
        }
    }
}