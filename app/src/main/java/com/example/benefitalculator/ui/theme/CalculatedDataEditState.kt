package com.example.benefitalculator.ui.theme

import androidx.lifecycle.LiveData
import com.example.benefitalculator.domain.CalculatedData

sealed class CalculatedDataEditState() {
    data object Initial: CalculatedDataEditState()

    data class CalcData(val productId: Int, val calcData: LiveData<List<CalculatedData>>) : CalculatedDataEditState()

}