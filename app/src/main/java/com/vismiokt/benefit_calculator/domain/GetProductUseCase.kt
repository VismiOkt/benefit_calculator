package com.vismiokt.benefit_calculator.domain

class GetProductUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun getProduct(productId: Int): Product {
        return benefitCalculatorRepository.getProduct(productId)
    }
}