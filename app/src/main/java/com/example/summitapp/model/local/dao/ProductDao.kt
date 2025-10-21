package com.example.summitapp.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.summitapp.model.data.Product


@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: Product)

    @Insert
    fun insertAllProducts(products: List<Product>)

    @Query("DELETE FROM product")
    fun clearAll()

    @Query("SELECT * FROM product")
    fun getAllProducts(): List<Product>

    @Update
    fun updateProduct(product: Product)

    @Query("SELECT * FROM product WHERE productId = :productId")
    fun getProductById(productId: Int): Product

    @Query("DELETE FROM product WHERE productId = :productId")
    fun deleteProduct(productId: Int)
}