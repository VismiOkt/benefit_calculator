package com.example.benefitalculator.domain

class AddCalculatedListUseCase (private val benefitCalculatorRepository: BenefitCalculatorRepository) {
    suspend fun addCalculatedDataList(id: Int, calcData: List<CalculatedData>) {

        benefitCalculatorRepository.addCalculatedData(calcData, id)

    }
}