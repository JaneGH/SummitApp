package com.example.summitapp.model.repository.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.summitapp.model.data.Product
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executors

class ProductRepository(
    private val localRepository: LocalProductRepository,
    private val remoteRepository: RemoteProductRepository
) : IProductRepository {

    override fun getProducts(): LiveData<List<Product>> {
        return localRepository.getProducts()
    }

    override fun updateProducts(q: String) {
        Thread {
            try {
                val future = remoteRepository.fetchProducts(q)
                val remoteList = future.get() ?: emptyList()
                localRepository.saveProducts(remoteList)
             } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}

//import com.example.summitapp.model.data.Product
//import com.example.summitapp.model.local.dao.ProductDao
//import com.example.summitapp.model.remote.ApiService

//class ProductRepository(
//    private val apiService: ApiService,
//    private val productDao: ProductDao
//) {
//
//    fun getProducts(q:String): List<Product> {
//        return try {
//            val response = apiService.getProducts(q).execute()
//            if (response.isSuccessful) {
//                val body = response.body()
//                if (body != null && body.status == 0) {
//                    val products = body.products
//                    productDao.clearAll()
//                    productDao.insertAllProducts(products)
//                    productDao.getAllProducts()
//                } else {
//                    productDao.getAllProducts()
//                }
//            } else {
//                productDao.getAllProducts()
//            }
//        } catch (e: Exception) {
//            productDao.getAllProducts()
//        }
//    }
//}