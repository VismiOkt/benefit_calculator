package com.example.benefitalculator.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductDbModel::class, CalculatedDataDbModel::class], version = 2, exportSchema = false)
abstract class BenefitCalculatorDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private var INSTANCE: BenefitCalculatorDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "benefit_calculator.db"

        fun getInstance(application: Application): BenefitCalculatorDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    BenefitCalculatorDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }

        }
    }


}