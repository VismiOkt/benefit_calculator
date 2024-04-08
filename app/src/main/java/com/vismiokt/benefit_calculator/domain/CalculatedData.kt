package com.vismiokt.benefit_calculator.domain

data class CalculatedData(
    val id: Int,
    var productId: Int = 0,
    var price: Double = 0.0,
    var weight: Double = 0.0,
    var resultPrice: Double = 0.0,
    var isBestPrice: Boolean = false,
    var errorInputWeight: Boolean = true,
    var errorInputPrice: Boolean = true
)

