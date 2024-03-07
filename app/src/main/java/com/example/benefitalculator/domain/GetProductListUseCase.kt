package com.example.benefitalculator.domain

import androidx.lifecycle.LiveData

class GetProductListUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    fun getProductList(): LiveData<List<Product>> {
        return benefitCalculatorRepository.getProductList()
    }
}