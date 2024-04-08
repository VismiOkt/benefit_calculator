package com.vismiokt.benefit_calculator.domain

class UpdateProductUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun updateProduct(product: Product) {
        benefitCalculatorRepository.editProduct(product)
    }
}