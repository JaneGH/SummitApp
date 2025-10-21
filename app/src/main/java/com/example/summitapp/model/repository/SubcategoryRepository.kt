package com.example.summitapp.model.repository

import com.example.summitapp.model.local.dao.SubcategoryDao
import com.example.summitapp.model.data.Subcategory
import com.example.summitapp.model.remote.ApiService

class SubcategoryRepository(
    private val apiService: ApiService,
    private val subcategoryDao: SubcategoryDao
) {

    fun getSubcategories(categoryId: String): List<Subcategory> {
        return try {
            val response = apiService.getSubcategories(categoryId).execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status == 0) {
                    val subcategories = body.subcategories
                    subcategoryDao.clearByCategoryId(categoryId)
                    subcategoryDao.insertAll(subcategories)
                    subcategoryDao.getSubcategoriesByCategoryId(categoryId)
                } else {
                    subcategoryDao.getSubcategoriesByCategoryId(categoryId)
                }
            } else {
                subcategoryDao.getSubcategoriesByCategoryId(categoryId)
            }
        } catch (e: Exception) {
            subcategoryDao.getSubcategoriesByCategoryId(categoryId)
        }
    }
}
