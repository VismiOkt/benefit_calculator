package com.example.benefitalculator.domain

class DeleteCalcDataUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun deleteCalcData(calcDataId: Int, productId: Int) {
        benefitCalculatorRepository.deleteCalcData(calcDataId, productId)
    }
}