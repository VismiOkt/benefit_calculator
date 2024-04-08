package com.vismiokt.benefit_calculator.domain

class GetCalculatedListUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun getCalculatedList(productId: Int): List<CalculatedData> {
        return benefitCalculatorRepository.getCalcData(productId)
    }
}