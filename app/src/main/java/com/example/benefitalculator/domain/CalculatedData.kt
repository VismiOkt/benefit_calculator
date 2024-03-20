package com.example.benefitalculator.domain

data class CalculatedData(
    val id: Int,
    var productId: Int = 0,
    var price: Double = 0.0,
    var weight: Double = 0.0,
    var resultPrice: Double = 0.0,
    var isBestPrice: Boolean = false,
    var errorInputWeight: Boolean = false,
    var errorInputPrice: Boolean = false
)
