package com.example.summitapp.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.summitapp.data.local.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Query("SELECT * FROM product")
    List<Product> getAllProducts();

    @Query("SELECT * FROM product WHERE id = :productId")
    Product getProductById(long productId);

    @Query("DELETE FROM product WHERE id = :productId")
    void deleteProduct(long productId);
}
