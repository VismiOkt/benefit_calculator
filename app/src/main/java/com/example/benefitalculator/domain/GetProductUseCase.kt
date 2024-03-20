package com.example.benefitalculator.domain

class GetProductUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun getProduct(productId: Int): Product {
        return benefitCalculatorRepository.getProduct(productId)
    }
}