package com.example.benefitalculator.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductDbModel): Long

    @Insert
    suspend fun addCalculatedData(calcData: List<CalculatedDataDbModel>)

    @Update
    suspend fun updateProduct(product: ProductDbModel)

    @Delete
    suspend fun deleteProduct(product: ProductDbModel)

    @Query("SELECT * FROM product_table")
    fun getProductList(): LiveData<List<ProductDbModel>>

    @Query("SELECT * FROM product_table WHERE id=:productId LIMIT 1")
    suspend fun getProduct(productId: Int): ProductDbModel

    @Query("SELECT * FROM calculated_data_table WHERE productId=:productId")
    suspend fun getCalcData(productId: Int): List<CalculatedDataDbModel>

    @Query("DELETE FROM calculated_data_table WHERE productId=:productId AND id=:calcDataId")
    suspend fun deleteCalcData(calcDataId: Int, productId: Int)

    @Query("DELETE FROM calculated_data_table WHERE productId=:productId")
    suspend fun deleteAllCalcData(productId: Int)






}