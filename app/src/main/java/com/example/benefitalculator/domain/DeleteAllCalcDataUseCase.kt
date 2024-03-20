package com.example.benefitalculator.domain

class DeleteAllCalcDataUseCase (private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun deleteAllCalcData(productId: Int) {
        benefitCalculatorRepository.deleteAllCalcData(productId)
    }
}