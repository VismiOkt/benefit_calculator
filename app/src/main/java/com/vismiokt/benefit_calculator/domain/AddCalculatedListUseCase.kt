package com.vismiokt.benefit_calculator.domain

class AddCalculatedListUseCase (private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun addCalculatedDataList(id: Int, calcData: List<CalculatedData>) {

        benefitCalculatorRepository.addCalculatedData(calcData, id)

    }
}