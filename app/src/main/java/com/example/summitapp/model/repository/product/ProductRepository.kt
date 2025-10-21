package com.example.summitapp.model.repository.product

import com.example.summitapp.model.data.Product
import com.example.summitapp.model.local.dao.ProductDao
import com.example.summitapp.model.remote.ApiService

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