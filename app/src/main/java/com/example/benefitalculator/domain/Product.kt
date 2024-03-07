package com.example.benefitalculator.domain

data class Product (
    var id: Int = UNDEFINED_ID,
    val name: String,
  //  val calcData: List<CalculatedData>,
    val note: String
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
