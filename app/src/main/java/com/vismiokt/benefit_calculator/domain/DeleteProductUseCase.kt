package com.vismiokt.benefit_calculator.domain

class DeleteProductUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun deleteProduct(product: Product) {
        benefitCalculatorRepository.deleteProduct(product)
    }
}