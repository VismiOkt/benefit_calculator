package com.example.benefitalculator.domain

class AddProductUseCase (private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun addProduct(product: Product): Int {
        var id = -1

        try {
            id = benefitCalculatorRepository.addProduct(product).toInt()

        } catch (ex: Exception) {

        }
        return id


    }
}