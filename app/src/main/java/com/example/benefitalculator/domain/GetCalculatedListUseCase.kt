package com.example.benefitalculator.domain

class GetCalculatedListUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun getCalculatedList(productId: Int): List<CalculatedData> {
        return benefitCalculatorRepository.getCalcData(productId)
    }
}