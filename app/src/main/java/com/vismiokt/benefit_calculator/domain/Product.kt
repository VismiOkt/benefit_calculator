package com.vismiokt.benefit_calculator.domain

data class Product (
    var id: Int = UNDEFINED_ID,
    val name: String,
    val note: String
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
