package com.example.benefitalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.ParseDataDoubleUseCase
import kotlin.math.roundToInt

class MainViewModel : ViewModel() {

    private val parseDataDoubleUseCase = ParseDataDoubleUseCase()

    private var count: Int = 2

    private val listCalculateDate = mutableListOf<CalculatedData>().apply {
        add(CalculatedData(0))
        add(CalculatedData(1))
    }

    private val _calculateData = MutableLiveData<List<CalculatedData>>(listCalculateDate)
    val calculateData: LiveData<List<CalculatedData>> = _calculateData

    private val _isLastCalculateData = MutableLiveData<Boolean>()
    val isLastCalcData: LiveData<Boolean> = _isLastCalculateData

    private fun isLastCalculateData() {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        _isLastCalculateData.value = cD.size < 2
    }


    private fun getResult(
        calcD: CalculatedData,
        price: Double,
        weight: Double,
        fieldValid: Boolean
    ) {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        val newCD = cD.apply {
            replaceAll {
                if (it.id == calcD.id) {
                    var resultPrice: Double = 0.0
                    if (fieldValid) {
                        resultPrice = (((price * 1.0) / weight) * 100).roundToInt() / 100.0
                    }
                    it.copy(price = price, weight = weight, resultPrice = resultPrice)

                } else {
                    it
                }
            }
        }
        _calculateData.value = newCD


    }

    fun calculate(inputPrice: String?, inputWeight: String?, calcD: CalculatedData) {
        val price = parseDataDoubleUseCase.parseData(inputPrice)
        val weight = parseDataDoubleUseCase.parseData(inputWeight)
        val fieldsValid = validateInput(price, weight, calcD)
        getResult(calcD, price, weight, fieldsValid)
        selectBestPrice()


    }

    private fun selectBestPrice() {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        cD.onEach {
            it.isBestPrice = false
        }
        try {
            val min = cD.filter { it.resultPrice != 0.0 }.minBy { it.resultPrice }
            val newCD = cD.apply {
                replaceAll {
                    if (it.id == min.id) {
                        it.copy(isBestPrice = true)
                    } else {
                        it.copy(isBestPrice = false)
                    }
                }
            }
            _calculateData.value = newCD
        } catch (e: NoSuchElementException) {
            return
        }

    }

    private fun validateInput(price: Double, weight: Double, calcD: CalculatedData): Boolean {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        var result = true
        val newCD = cD.apply {
            replaceAll {

                if (calcD.id == it.id) {
                    var errorPrice = false
                    var errorWeight = false
                    if (price <= 0.0) {
                        errorPrice = true
                        result = false
                    }
                    if (weight <= 0.0) {
                        errorWeight = true
                        result = false
                    }
                    it.copy(errorInputPrice = errorPrice, errorInputWeight = errorWeight)
                } else {
                    it
                }
            }
        }
        _calculateData.value = newCD
        return result
    }


    fun resetErrorInputPrice(calcD: CalculatedData) {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        val newCD = cD.apply {
            replaceAll {
                if (it.id == calcD.id) {
                    it.copy(errorInputPrice = false)
                } else {
                    it
                }
            }
        }
        _calculateData.value = newCD
    }

    fun resetErrorInputWeight(calcD: CalculatedData) {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        val newCD = cD.apply {
            replaceAll {
                if (it.id == calcD.id) {
                    it.copy(errorInputWeight = false)
                } else {
                    it
                }
            }
        }
        _calculateData.value = newCD

    }

    fun addNewCalculateData() {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        cD.add(CalculatedData(id = count++))
        _calculateData.value = cD
        isLastCalculateData()
    }

    fun deleteCalculateData(calcD: CalculatedData) {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        if (cD.size > 1) {
            cD.remove(calcD)
            _calculateData.value = cD
        }
        isLastCalculateData()
        selectBestPrice()
    }

    fun resetAllCalculateData() {
        val cD = mutableListOf<CalculatedData>().apply {
            add(CalculatedData(count++))
            add(CalculatedData(count++))
        }
        _calculateData.value = cD

    }
}