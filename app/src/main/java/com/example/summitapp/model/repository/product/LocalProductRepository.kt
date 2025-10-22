package com.example.summitapp.model.repository.product


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.summitapp.model.data.Product
import com.example.summitapp.model.local.dao.ProductDao

class LocalProductRepository(private val productDao: ProductDao) {

    fun getProducts(): LiveData<List<Product>> {
        val liveData = MutableLiveData<List<Product>>()
        Thread {
            liveData.postValue(productDao.getAllProducts())
        }.start()
        return liveData
    }

    fun saveProducts(products: List<Product>) {
        productDao.clearAll()
        productDao.insertAllProducts(products)
    }
}
