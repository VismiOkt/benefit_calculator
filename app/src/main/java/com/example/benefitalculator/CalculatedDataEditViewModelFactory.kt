package com.example.benefitalculator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.benefitalculator.domain.Product

class CalculatedDataEditViewModelFactory(
    private val productId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalculatedDataEditViewModel(productId = productId, application = Application()) as T
    }
}