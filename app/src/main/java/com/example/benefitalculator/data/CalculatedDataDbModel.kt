package com.example.benefitalculator.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (
    tableName = "calculated_data_table",
    foreignKeys = [ForeignKey(
        entity = ProductDbModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("productId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)]
)
data class CalculatedDataDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    var price: Double,
    var weight: Double,
    var resultPrice: Double,
    var isBestPrice: Boolean
)