package com.example.benefitalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.benefitalculator.data.BenefitCalculatorRepositoryImpl
import com.example.benefitalculator.domain.AddProductUseCase
import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.DeleteProductUseCase
import com.example.benefitalculator.domain.GetProductUseCase
import com.example.benefitalculator.domain.Product
import com.example.benefitalculator.domain.UpdateProductUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ProductViewModel(application: Application): AndroidViewModel(application) {
    private val repository = BenefitCalculatorRepositoryImpl(application)

    private val getProductUseCase = GetProductUseCase(repository)
    private val updateProductUseCase = UpdateProductUseCase(repository)
    private val deleteProductUseCase = DeleteProductUseCase(repository)
    private val addProductUseCase = AddProductUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> = _errorInputName

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

//    private val _closeScreen = MutableLiveData<Unit>()
//    val closeScreen: LiveData<Unit> = _closeScreen

    fun getProduct(productId: Int) {
        scope.launch {
            val prod = getProductUseCase.getProduct(productId)
            _product.value = prod
        }

    }

    fun editProduct(nameProduct: String?, noteProduct: String?, calcData: List<CalculatedData>) {
        val name = parseData(nameProduct)
        val note = parseData(noteProduct)
        if (validate(name)) {
            _product.value?.let {
                scope.launch {
                    val product = it.copy(name = name, note = note)
                    updateProductUseCase.updateProduct(product)
                }


  //              finishWork()
            }

        }
    }

    fun addProduct(nameProduct: String?, noteProduct: String?, calcData: List<CalculatedData>) {
        val name = parseData(nameProduct)
        val note = parseData(noteProduct)
        if (validate(name)) {
            scope.launch {
                val product = Product(name = name, note = note)
                addProductUseCase.addProduct(product, calcData)

            }

 //           finishWork()
        }
    }

    fun parseData(input: String?): String {
        return input?.trim() ?: ""
    }

    fun validate(name: String): Boolean {
        var result = true
        if (name == "") {
            _errorInputName.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    //    private fun finishWork() {
//        _closeScreen.value = Unit
//    }
    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}