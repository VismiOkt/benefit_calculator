package com.example.benefitalculator.domain

import androidx.lifecycle.LiveData

interface BenefitCalculatorRepository {
    suspend fun addProduct(product: Product): Long

   // suspend fun addProductAndCalculatedData(product: Product, calcData: List<CalculatedData>)

    suspend fun deleteProduct(product: Product)

    suspend fun editProduct(product: Product)

    suspend fun getProduct(productId: Int): Product

    suspend fun addCalculatedData(calcData: List<CalculatedData>, productId: Int)

//    suspend fun getCalculatedList(product: Product): LiveData<List<CalculatedData>>

    fun getProductList(): LiveData<List<Product>>

    fun getCalcData(productId: Int): LiveData<List<CalculatedData>>

}