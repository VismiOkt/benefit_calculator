package com.example.benefitalculator.ui.theme

import androidx.lifecycle.LiveData
import com.example.benefitalculator.domain.Product

sealed class ProductScreenState(

) {
    data object Initial: ProductScreenState()
    data class Products(val products: LiveData<List<Product>>) : ProductScreenState()



}