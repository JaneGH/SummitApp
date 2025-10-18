package com.example.summitapp.data.local.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecommersproject.model.Category

@Dao
interface CategoryDao {

    @Insert
    fun insertCategory(category: Category)

    @Update
    fun updateCategory(category: Category)

    @Query("SELECT * FROM category")
    fun getAllCategories(): List<Category>

    @Query("SELECT * FROM category WHERE localId = :categoryId")
    fun getCategoryById(categoryId: Long): Category?

    @Query("DELETE FROM category WHERE localId = :categoryId")
    fun deleteCategory(categoryId: Long)

    @Query("DELETE FROM category")
    fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Category>)
}
