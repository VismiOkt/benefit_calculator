package com.example.benefitalculator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "calculated_data_table")
data class CalculatedDataDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val productId: Int,
    var price: Double,
    var weight: Double,
    var resultPrice: Double,
    var isBestPrice: Boolean
) {
}