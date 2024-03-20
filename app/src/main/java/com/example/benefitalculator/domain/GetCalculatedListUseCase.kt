package com.example.benefitalculator.domain

import androidx.lifecycle.LiveData

class GetCalculatedListUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun getCalculatedList(productId: Int): List<CalculatedData> {
        return benefitCalculatorRepository.getCalcData(productId)
    }
}