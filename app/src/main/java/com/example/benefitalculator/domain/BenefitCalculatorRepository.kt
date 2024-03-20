package com.example.benefitalculator.domain

import androidx.lifecycle.LiveData
import com.example.benefitalculator.data.CalculatedDataDbModel

interface BenefitCalculatorRepository {
    suspend fun addProduct(product: Product): Long

    suspend fun deleteProduct(product: Product)

    suspend fun editProduct(product: Product)

    suspend fun getProduct(productId: Int): Product

    suspend fun addCalculatedData(calcData: List<CalculatedData>, productId: Int)

//    suspend fun updateCalculatedData(calcData: List<CalculatedData>, productId: Int)

//    suspend fun getCalculatedList(product: Product): LiveData<List<CalculatedData>>

    fun getProductList(): LiveData<List<Product>>

    suspend fun getCalcData(productId: Int): List<CalculatedData>

//    suspend fun getBestPrice(productId: Int): Double

    suspend fun deleteCalcData(calcDataId: Int, productId: Int)

    suspend fun deleteAllCalcData(productId: Int)


}