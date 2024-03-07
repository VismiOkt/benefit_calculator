package com.example.benefitalculator.domain

class UpdateProductUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun updateProduct(product: Product) {
        benefitCalculatorRepository.editProduct(product)
    }
}