package com.example.benefitalculator.domain

import androidx.lifecycle.LiveData

interface BenefitCalculatorRepository {
    suspend fun addProduct(product: Product, calcData: CalculatedData)

    suspend fun deleteProduct(product: Product)

    suspend fun editProduct(product: Product)

    suspend fun getProduct(productId: Int): Product

    fun getProductList(): LiveData<List<Product>>

    fun getCalcData(productId: Int): LiveData<List<CalculatedData>>
}