package com.example.benefitalculator.domain

import androidx.lifecycle.LiveData

class GetCalculatedListUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    fun getCalculatedList(product: Product): LiveData<List<CalculatedData>> {
        return benefitCalculatorRepository.getCalcData(productId = product.id)
    }
}