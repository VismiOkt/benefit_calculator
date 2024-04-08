package com.vismiokt.benefit_calculator.domain

import java.lang.Exception

class ParseDataDoubleUseCase {
    fun parseData(input: String?): Double {
        return try{
            input?.trim()?.toDouble() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    }
}