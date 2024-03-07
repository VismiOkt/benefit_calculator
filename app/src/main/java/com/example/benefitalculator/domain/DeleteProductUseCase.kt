package com.example.benefitalculator.domain

class DeleteProductUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun deleteProduct(product: Product) {
        benefitCalculatorRepository.deleteProduct(product)
    }
}