package com.example.summitapp.model.repository.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.summitapp.model.data.Category
import java.util.concurrent.Executors

class CategoryRepository(
    private val localRepository: LocalCategoryRepository,
    private val remoteRepository: RemoteCategoryRepository
) : ICategoryRepository {

    override fun getCategories(): LiveData<List<Category>> {
        return localRepository.getCategories()
    }

    override fun updateCategories() {
        Executors.newSingleThreadExecutor().execute {
            try {
                val remoteCategories = remoteRepository.fetchCategories().get()
                if (!remoteCategories.isNullOrEmpty()) {
                    localRepository.saveCategories(remoteCategories)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
