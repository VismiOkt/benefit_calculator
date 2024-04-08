package com.vismiokt.benefit_calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vismiokt.benefit_calculator.data.BenefitCalculatorRepositoryImpl
import com.vismiokt.benefit_calculator.domain.AddCalculatedListUseCase
import com.vismiokt.benefit_calculator.domain.AddProductUseCase
import com.vismiokt.benefit_calculator.domain.CalculatedData
import com.vismiokt.benefit_calculator.domain.DeleteProductUseCase
import com.vismiokt.benefit_calculator.domain.GetProductListUseCase
import com.vismiokt.benefit_calculator.domain.GetProductUseCase
import com.vismiokt.benefit_calculator.domain.Product
import com.vismiokt.benefit_calculator.domain.UpdateProductUseCase
import com.vismiokt.benefit_calculator.ui.theme.ProductScreenState
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BenefitCalculatorRepositoryImpl(application)

    private val getProductUseCase = GetProductUseCase(repository)
    private val updateProductUseCase = UpdateProductUseCase(repository)
    private val deleteProductUseCase = DeleteProductUseCase(repository)
    private val addProductUseCase = AddProductUseCase(repository)
    private val addCalculatedListUseCase = AddCalculatedListUseCase(repository)
    private val getProductListUseCase = GetProductListUseCase(repository)

    private val productList: LiveData<List<Product>> = getProductListUseCase.getProductList()
    private val initialState = ProductScreenState.Products(productList)

    private val _productListSearch = MutableLiveData<List<Product>>(listOf())
    val productListSearch: LiveData<List<Product>> = _productListSearch

    private val _screenState = MutableLiveData<ProductScreenState>(initialState)
    val screenState: LiveData<ProductScreenState> = _screenState

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> = _errorInputName

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    fun getProduct(productId: Int) {
        viewModelScope.launch {
            val prod = getProductUseCase.getProduct(productId)
            _product.value = prod
        }

    }

    fun editProduct(product: Product, nameProduct: String?, noteProduct: String?) {
        getProduct(product.id)
        val name = parseData(nameProduct)
        val note = parseData(noteProduct)
        if (validate(name)) {
            _product.value?.let {
                viewModelScope.launch {
                    val newProduct = it.copy(name = name, note = note)
                    updateProductUseCase.updateProduct(newProduct)
                }
            }

        }
    }

    fun addProduct(nameProduct: String?, noteProduct: String?, calcData: List<CalculatedData>) {
        val name = parseData(nameProduct)
        val note = parseData(noteProduct)
        if (validate(name)) {
            var id = -1
            viewModelScope.launch {
                val product = Product(name = name, note = note)
                id = addProductUseCase.addProduct(product)
                addCalculatedListUseCase.addCalculatedDataList(id, calcData)

            }
        }
    }

    private fun parseData(input: String?): String {
        return input?.trim() ?: ""
    }

    private fun validate(name: String): Boolean {
        var result = true
        if (name == "") {
            _errorInputName.value = true
            result = false
        }
        return result
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase.deleteProduct(product)
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun searchProduct(inputText: String){
        val pL = productList.value?.toMutableList() ?: mutableListOf()
        val new = pL.filter {
            it.name.contains(inputText, ignoreCase = true)
        }
        _productListSearch.value = new
    }

    fun initialSearchProductList() {
        val pL = productList.value?.toMutableList() ?: mutableListOf()
        _productListSearch.value = pL
    }




}