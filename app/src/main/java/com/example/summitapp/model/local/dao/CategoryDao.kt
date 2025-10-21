package com.example.summitapp.model.local.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.summitapp.model.data.Category

@Dao
interface CategoryDao {

    @Insert
    fun insertCategory(category: Category)

    @Update
    fun updateCategory(category: Category)

    @Query("SELECT * FROM category")
    fun getAllCategories():  LiveData<List<Category>>

    @Query("SELECT * FROM category WHERE localId = :categoryId")
    fun getCategoryById(categoryId: Long): Category?

    @Query("DELETE FROM category WHERE localId = :categoryId")
    fun deleteCategory(categoryId: Long)

    @Query("DELETE FROM category")
    fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Category>)
}
