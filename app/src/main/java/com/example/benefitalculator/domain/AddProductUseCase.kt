package com.example.benefitalculator.domain

class AddProductUseCase (private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun addProduct(product: Product, calcData: CalculatedData) {
        benefitCalculatorRepository.addProduct(product, calcData)
    }
}