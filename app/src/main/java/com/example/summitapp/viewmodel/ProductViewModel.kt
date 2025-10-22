package com.example.summitapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.summitapp.SummitApp
import com.example.summitapp.model.data.Product
import com.example.summitapp.model.data.Subcategory
import com.example.summitapp.model.local.database.AppDatabase
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.repository.SubcategoryRepository
import com.example.summitapp.model.repository.product.IProductRepository
import com.example.summitapp.model.repository.product.LocalProductRepository
import com.example.summitapp.model.repository.product.ProductRepository
import com.example.summitapp.model.repository.product.RemoteProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(
    application: Application,
    private val productRepository: IProductRepository,
    private val subcategoryRepository: SubcategoryRepository
) : AndroidViewModel(application) {

    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> = _products

    private val _subcategories = MutableLiveData<List<Subcategory>>(emptyList())
    val subcategories: LiveData<List<Subcategory>> = _subcategories

    private var selectedSubcategoryId: Int? = null
    private var categoryId: Int = 0

    private var currentProducts: List<Product> = emptyList()

    fun setCategoryId(categoryId: Int, lifecycleOwner: LifecycleOwner) {
        this.categoryId = categoryId
        loadSubcategories()

       productRepository.getProducts().observe(lifecycleOwner) { list ->
            currentProducts = list
            filterProducts()
        }

        productRepository.updateProducts("")
    }

    fun selectSubcategory(subcategoryId: Int?) {
        selectedSubcategoryId = subcategoryId
        filterProducts()
    }

    private fun filterProducts() {
        if (currentProducts.isEmpty()) return
        val filtered = currentProducts.filter { product ->
            val matchesCategory = product.categoryId == categoryId
            val matchesSubcategory = selectedSubcategoryId?.let { it == product.subcategoryId } ?: true
            matchesCategory && matchesSubcategory
        }
        _products.postValue(filtered)
    }

    private fun loadSubcategories() {
        Thread {
            try {
                val subs = subcategoryRepository.getSubcategories(categoryId.toString())
                _subcategories.postValue(subs)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory{
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SummitApp)
                val db = AppDatabase.getInstance(app.applicationContext)
                val localRepo = LocalProductRepository(db.productDao())
                val remoteRepo = RemoteProductRepository(
                    ApiService.getInstance())
                val productRepo = ProductRepository(localRepo, remoteRepo)
                val subcategoryRepo = SubcategoryRepository(ApiService.getInstance(), db.subcategoryDao())
                ProductViewModel(app, productRepo, subcategoryRepo)
            }
        }
    }
}
