package com.example.summitapp.ui.viewmodel

import androidx.lifecycle.*
import com.example.summitapp.data.model.Category
import com.example.summitapp.data.repository.CategoryRepository
import java.util.concurrent.Executors

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val executor = Executors.newSingleThreadExecutor()

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun fetchCategories() {
        executor.execute {
            val result = repository.getCategories()
            _categories.postValue(result)
        }
    }
}

class CategoryViewModelFactory(
    private val repository: CategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
