package com.vismiokt.benefit_calculator.domain

import java.lang.Exception

class ParseDataDoubleUseCase {
    fun parseData(input: String?): Double = input?.trim()?.toDoubleOrNull() ?: 0.0
}