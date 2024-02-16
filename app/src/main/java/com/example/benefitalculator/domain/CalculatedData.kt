package com.example.benefitalculator.domain

data class CalculatedData(
    val id: Int,
    var price: String = "",
    var weight: String = "",
    var resultPrice: Double = 0.0
)
