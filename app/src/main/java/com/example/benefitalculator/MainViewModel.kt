package com.example.benefitalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.benefitalculator.domain.CalculatedData
import java.lang.Exception

class MainViewModel: ViewModel() {

    val listCalculateDate = mutableListOf<CalculatedData>().apply {
        add(CalculatedData(0))
    }

    private val _calculateData = MutableLiveData<List<CalculatedData>>(listCalculateDate)
    val calculateData: LiveData<List<CalculatedData>> = _calculateData

    fun getResult(calcD: CalculatedData) {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        val newCD = cD.apply {
            replaceAll {
                if (it == calcD) {
                    if (it.price != "" && it.weight != "") {
                        it.copy(resultPrice = (it.price.toDouble() * 1.0) / it.weight.toDouble())
                    } else {
                        it
                    }

                } else {
                    it
                }
            }
        }
        _calculateData.value = newCD


    }

    fun calculate(inputPrice: String?, inputWeight: String?) {
        val price = parseData(inputPrice)
        val weight = parseData(inputWeight)
        val fieldsValid = validateInput(price, weight)
        if (fieldsValid) {
            getResult()
        }
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
        if (price == 0.0) {
            // TODO: show error
            result = false
        }
        if (weight <= 0.0) {
            // TODO: show error
            result = false
        }
        return result
    }
}