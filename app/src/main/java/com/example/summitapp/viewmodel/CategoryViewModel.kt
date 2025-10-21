package com.example.summitapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.summitapp.SummitApp
import com.example.summitapp.model.data.Category
import com.example.summitapp.model.local.database.AppDatabase
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.repository.category.CategoryRepository
import com.example.summitapp.model.repository.category.ICategoryRepository
import com.example.summitapp.model.repository.category.LocalCategoryRepository
import com.example.summitapp.model.repository.category.RemoteCategoryRepository
import java.util.concurrent.Executors

class CategoryViewModel(application: Application, private val repository: ICategoryRepository) : ViewModel() {

    val categories: LiveData<List<Category>> = repository.getCategories()

    fun fetchCategories() {
        repository.updateCategories()
    }
companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = (this[APPLICATION_KEY] as SummitApp)
            val appDatabase = AppDatabase.getInstance(application.applicationContext)
            val localRepository = LocalCategoryRepository(appDatabase)
            val remoteRepository = RemoteCategoryRepository(ApiService.getInstance())
            val repository = CategoryRepository(localRepository, remoteRepository)
            CategoryViewModel(application, repository)
        }
    }
}
}

