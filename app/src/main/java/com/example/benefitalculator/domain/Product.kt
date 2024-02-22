package com.example.benefitalculator.domain

data class Product (
    val id: Int,
    val name: String,
    val calcData: List<CalculatedData>,
    val note: String
)
