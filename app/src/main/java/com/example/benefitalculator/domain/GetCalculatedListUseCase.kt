package com.example.benefitalculator.domain

import androidx.lifecycle.LiveData

class GetCalculatedListUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun getCalculatedList(product: Product): List<CalculatedData> {
        return benefitCalculatorRepository.getCalcData(productId = product.id)
    }
}