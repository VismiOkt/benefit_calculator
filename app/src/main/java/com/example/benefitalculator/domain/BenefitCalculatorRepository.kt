package com.example.benefitalculator.domain

import androidx.lifecycle.LiveData

interface BenefitCalculatorRepository {
    fun addProduct(product: Product)

    fun deleteProduct(product: Product)

    fun editProduct(product: Product)

    fun getProduct(productId: Int): Product

    fun getProductList(): LiveData<List<Product>>
}