package com.example.benefitalculator.ui.theme

import androidx.lifecycle.LiveData
import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.Product

sealed class CalculatedDataEditState(

) {
    data object Initial: CalculatedDataEditState()

    data class CalcData(val product: Product, val calcData: LiveData<List<CalculatedData>>) : CalculatedDataEditState()

}