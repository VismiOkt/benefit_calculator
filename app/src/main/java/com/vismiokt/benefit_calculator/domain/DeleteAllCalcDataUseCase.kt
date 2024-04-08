package com.vismiokt.benefit_calculator.domain

class DeleteAllCalcDataUseCase (private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun deleteAllCalcData(productId: Int) {
        benefitCalculatorRepository.deleteAllCalcData(productId)
    }
}