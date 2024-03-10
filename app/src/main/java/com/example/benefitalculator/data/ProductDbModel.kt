package com.example.benefitalculator.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.benefitalculator.domain.CalculatedData

@Entity(tableName = "product_table")
data class ProductDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "product_name")
    var name: String,
    @ColumnInfo(name = "product_note")
    var note: String
)