package com.vismiokt.benefit_calculator.ui

import androidx.lifecycle.LiveData
import com.vismiokt.benefit_calculator.domain.Product

sealed class ProductScreenState() {
    data object Initial: ProductScreenState()

    data class Products(val products: LiveData<List<Product>>) : ProductScreenState()

}