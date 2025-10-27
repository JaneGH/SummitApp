package com.example.summitapp.model.repository.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.summitapp.model.data.Product
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executors
import kotlin.collections.emptyList

class ProductRepository(
    private val localRepository: LocalProductRepository,
    private val remoteRepository: RemoteProductRepository
) : IProductRepository {

    override  fun getProducts(): LiveData<List<Product>> {
        return localRepository.getProducts()
    }

    override suspend fun updateProducts(q: String) {
            try {
                val remoteList = remoteRepository.fetchProducts(q) ?: emptyList()
                localRepository.saveProducts(remoteList)
             } catch (e: Exception) {
                e.printStackTrace()
            }
    }
}
