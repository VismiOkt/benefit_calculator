package com.example.benefitalculator.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity (
    tableName = "calculated_data_table",
    foreignKeys = [ForeignKey(
        entity = ProductDbModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("productId"),
        onDelete = CASCADE,
        onUpdate = CASCADE
            )]
)
data class CalculatedDataDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val productId: Int,
    var price: Double,
    var weight: Double,
    var resultPrice: Double,
    var isBestPrice: Boolean,
    var errorInputWeight: Boolean,
    var errorInputPrice: Boolean
)