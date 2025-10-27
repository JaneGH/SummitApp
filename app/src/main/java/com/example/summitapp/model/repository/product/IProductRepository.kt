package com.example.summitapp.model.repository.product

import androidx.lifecycle.LiveData
import com.example.summitapp.model.data.Product

interface IProductRepository {
    fun getProducts(): LiveData<List<Product>>
    suspend fun updateProducts(q:String)
}
