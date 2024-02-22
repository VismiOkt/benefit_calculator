package com.example.benefitalculator

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.benefitalculator.domain.CalculatedData
import java.lang.Exception
import kotlin.math.roundToInt

class MainViewModel: ViewModel() {

    private  var count: Int = 2

    private val listCalculateDate = mutableListOf<CalculatedData>().apply {
        add(CalculatedData(0))
        add(CalculatedData(1))
    }

    private val _calculateData = MutableLiveData<List<CalculatedData>>(listCalculateDate)
    val calculateData: LiveData<List<CalculatedData>> = _calculateData

    private val _errorInputPrice = MutableLiveData<Boolean>()
    val errorInputPrice: LiveData<Boolean> = _errorInputPrice

    private val _errorInputWeight = MutableLiveData<Boolean>()
    val errorInputWeight: LiveData<Boolean> = _errorInputWeight

    private val _isLastCalculateData = MutableLiveData<Boolean>()
    val isLastCalcData: LiveData<Boolean> = _isLastCalculateData

    private fun isLastCalculateData() {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        _isLastCalculateData.value = cD.size < 2
    }


    private fun getResult(calcD: CalculatedData, price: Double, weight: Double, fieldValid: Boolean) {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        val newCD = cD.apply {
            replaceAll {
                if (it.id == calcD.id) {
                    var resultPrice = 0.0
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
        val price = parseData(inputPrice)
        val weight = parseData(inputWeight)
        val fieldsValid = validateInput(price, weight)
        getResult(calcD, price, weight, fieldsValid)
        selectBestPrice()


    }
    private fun selectBestPrice() {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        val h = cD.filter { it.resultPrice != 0.0 }.minBy { it.resultPrice }
        val newCD = cD.apply {
            replaceAll {
                if (it.id == h.id) {
                    it.copy(isBestPrice = true)
                } else {
                    it.copy(isBestPrice = false)
                }
            }
        }
        _calculateData.value = newCD
    }

    private fun parseData(input: String?): Double {
        return try{
            input?.trim()?.toDouble() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    }

    private fun validateInput(price: Double, weight: Double): Boolean {
        var result = true
        if (price <= 0.0) {
            _errorInputPrice.value = true
            result = false
        }
        if (weight <= 0.0) {
            _errorInputWeight.value = true
            result = false
        }
        return result
    }


    fun resetErrorInputPrice() {
        _errorInputPrice.value = false
    }

    fun resetErrorInputWeight() {
        _errorInputWeight.value = false
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
}