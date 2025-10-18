package com.example.summitapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.summitapp.data.local.entity.Product


@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: Product)

    @Insert
    fun insertAllProducts(products: List<Product>)

    @Query("SELECT * FROM product")
    fun getAllProducts(): List<Product>

    @Update
    fun updateProduct(product: Product)

    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductById(productId: Long): Product

    @Query("DELETE FROM product WHERE id = :productId")
    fun deleteProduct(productId: Long)
}