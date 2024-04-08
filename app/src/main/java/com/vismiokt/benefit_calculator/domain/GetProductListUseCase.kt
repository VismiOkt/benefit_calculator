package com.vismiokt.benefit_calculator.domain

import androidx.lifecycle.LiveData

class GetProductListUseCase(private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    fun getProductList(): LiveData<List<Product>> {
        return benefitCalculatorRepository.getProductList()
    }
}