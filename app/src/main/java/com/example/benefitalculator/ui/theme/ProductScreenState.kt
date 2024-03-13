package com.example.benefitalculator.ui.theme

import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.Product

sealed class ProductScreenState(

) {
    data class Products(val products: List<Product>) : ProductScreenState()

    data class CalcData(val calcData: List<CalculatedData>) : ProductScreenState()

}