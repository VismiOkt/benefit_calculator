package com.example.benefitalculator.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.benefitalculator.domain.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(product: ProductDbModel)

    @Update
    fun updateProduct(product: ProductDbModel)

    @Delete
    fun deleteProduct(product: ProductDbModel)

    @Query("SELECT * FROM product_table")
    fun getProductList(): LiveData<List<ProductDbModel>>

    @Query("SELECT * FROM product_table WHERE id=:productId LIMIT 1")
    fun getProduct(productId: Int): ProductDbModel





}