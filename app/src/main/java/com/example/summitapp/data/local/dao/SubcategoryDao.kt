package com.example.summitapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.summitapp.data.model.Subcategory

@Dao
interface SubcategoryDao {

    @Query("SELECT * FROM subcategories WHERE categoryId = :categoryId")
    fun getSubcategoriesByCategoryId(categoryId: String): List<Subcategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(subcategories: List<Subcategory>)

    @Query("DELETE FROM subcategories WHERE categoryId = :categoryId")
    fun clearByCategoryId(categoryId: String)
}