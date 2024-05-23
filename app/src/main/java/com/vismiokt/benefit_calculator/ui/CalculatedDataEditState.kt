package com.vismiokt.benefit_calculator.ui

import androidx.lifecycle.LiveData
import com.vismiokt.benefit_calculator.domain.CalculatedData

sealed class CalculatedDataEditState() {
    data object Initial: CalculatedDataEditState()

    data class CalcData(val productId: Int, val calcData: LiveData<List<CalculatedData>>) : CalculatedDataEditState()

}