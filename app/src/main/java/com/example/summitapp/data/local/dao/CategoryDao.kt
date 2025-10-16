package com.example.summitapp.data.local.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.summitapp.data.local.entity.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT * FROM category WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): Category?

    @Query("DELETE FROM category WHERE id = :categoryId")
    suspend fun deleteCategory(categoryId: Long)
}
