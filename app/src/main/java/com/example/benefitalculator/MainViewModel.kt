package com.example.benefitalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.benefitalculator.domain.CalculatedData

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
}