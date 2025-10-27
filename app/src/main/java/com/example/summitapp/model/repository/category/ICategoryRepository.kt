package com.example.summitapp.model.repository.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.summitapp.model.data.Category

interface ICategoryRepository {
    fun getCategories(): LiveData<List<Category>>
    suspend fun updateCategories()
}
