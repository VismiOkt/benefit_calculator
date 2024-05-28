package com.vismiokt.benefit_calculator.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vismiokt.benefit_calculator.data.BenefitCalculatorRepositoryImpl
import com.vismiokt.benefit_calculator.domain.AddCalculatedListUseCase
import com.vismiokt.benefit_calculator.domain.CalculatedData
import com.vismiokt.benefit_calculator.domain.DeleteAllCalcDataUseCase
import com.vismiokt.benefit_calculator.domain.GetCalculatedListUseCase
import com.vismiokt.benefit_calculator.domain.ParseDataDoubleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class CalculatedDataEditViewModel(
    productId: Int,
    application: Application
) : AndroidViewModel(application) {
    private val repository = BenefitCalculatorRepositoryImpl(application)
    private val parseDataDoubleUseCase = ParseDataDoubleUseCase()

    private val getCalculatedListUseCase = GetCalculatedListUseCase(repository)
    private val addCalculatedListUseCase = AddCalculatedListUseCase(repository)
    private val deleteAllCalcDataUseCase = DeleteAllCalcDataUseCase(repository)

    private val _calculateData = MutableLiveData<List<CalculatedData>>(listOf())
    val calculatedData: LiveData<List<CalculatedData>> = _calculateData

    private val initialState = CalculatedDataEditState.CalcData(productId, _calculateData)

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState


    private val _screenState = MutableLiveData<CalculatedDataEditState>(initialState)
    val screenState: LiveData<CalculatedDataEditState> = _screenState

    private val _isLastCalculateData = MutableLiveData<Boolean>()
    val isLastCalcData: LiveData<Boolean> = _isLastCalculateData

    private var count: Int = 2

    init {
        initialList(productId)
    }


    private fun initialList(productId: Int) {
        viewModelScope.launch {
            _calculateData.value = getCalculatedListUseCase.getCalculatedList(productId)
            isLastCalculateData()
            val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
            count = cD.last().id + 1
        }

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
        val price = parseDataDoubleUseCase.parseData(inputPrice)
        val weight = parseDataDoubleUseCase.parseData(inputWeight)
        val fieldsValid = validateInput(price, weight, calcD)
        getResult(calcD, price, weight, fieldsValid)
        selectBestPrice()
    }

    fun saveChangesCalculationData(productId: Int) {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        viewModelScope.launch {
            deleteAllCalcDataUseCase.deleteAllCalcData(productId)
            addCalculatedListUseCase.addCalculatedDataList(productId, cD)
        }
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

    private fun isLastCalculateData() {
        val cD = _calculateData.value?.toMutableList() ?: mutableListOf()
        _isLastCalculateData.value = cD.size < 2
    }


}