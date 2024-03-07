package com.example.benefitalculator.domain

import android.adservices.adid.AdId

class GetProductUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun getProduct(productId: Int): Product {
        return benefitCalculatorRepository.getProduct(productId)
    }
}