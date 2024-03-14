package com.example.benefitalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.benefitalculator.data.BenefitCalculatorRepositoryImpl
import com.example.benefitalculator.domain.AddCalculatedListUseCase
import com.example.benefitalculator.domain.AddProductUseCase
import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.DeleteCalcDataUseCase
import com.example.benefitalculator.domain.DeleteProductUseCase
import com.example.benefitalculator.domain.GetBestPriceUseCase
import com.example.benefitalculator.domain.GetCalculatedListUseCase
import com.example.benefitalculator.domain.GetProductListUseCase
import com.example.benefitalculator.domain.GetProductUseCase
import com.example.benefitalculator.domain.Product
import com.example.benefitalculator.domain.UpdateProductUseCase
import com.example.benefitalculator.ui.theme.ProductScreenState
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BenefitCalculatorRepositoryImpl(application)

    private val getProductUseCase = GetProductUseCase(repository)
    private val updateProductUseCase = UpdateProductUseCase(repository)
    private val deleteProductUseCase = DeleteProductUseCase(repository)
    private val addProductUseCase = AddProductUseCase(repository)
    private val addCalculatedListUseCase = AddCalculatedListUseCase(repository)
    private val getProductListUseCase = GetProductListUseCase(repository)
    private val getCalculatedListUseCase = GetCalculatedListUseCase(repository)
    private val getBestPriceUseCase = GetBestPriceUseCase(repository)
    private val deleteCalcDataUseCase = DeleteCalcDataUseCase(repository)

    private val productList: LiveData<List<Product>> = getProductListUseCase.getProductList()
    private val initialState = ProductScreenState.Products(productList)

    private val _screenState = MutableLiveData<ProductScreenState>(initialState)
    val screenState: LiveData<ProductScreenState> = _screenState

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> = _errorInputName

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product




//    private val _bestPrice = MutableLiveData<Double>()
//    val bestPrice: LiveData<Double> = _bestPrice


//    private var _calcDataListProduct: LiveData<List<CalculatedData>> = MutableLiveData<List<CalculatedData>>()
//    val calcDataListProduct: LiveData<List<CalculatedData>> = _calcDataListProduct

//    private val _closeScreen = MutableLiveData<Unit>()
//    val closeScreen: LiveData<Unit> = _closeScreen

//    fun showCalcData(product: Product) {
//        getCalcData(product)
//        _screenState.value = ProductScreenState.CalcData(product, calcDataListProduct)
//
//    }

    fun getProduct(productId: Int) {
        viewModelScope.launch {
            val prod = getProductUseCase.getProduct(productId)
            _product.value = prod
        }

    }

    fun getBestPrice(product: Product): String {
        var bestPrice: Double = 0.0
        viewModelScope.launch {
            bestPrice = getBestPriceUseCase.getBestPrice(product.id)
        }
        return bestPrice.toString()
    }

//    private fun getCalcData(product: Product) {
//        val calcDataList = getCalculatedListUseCase.getCalculatedList(product)
//        _calcDataListProduct = calcDataList
//    }


    fun editProduct(product: Product, nameProduct: String?, noteProduct: String?) {
        getProduct(product.id)
        val name = parseData(nameProduct)
        val note = parseData(noteProduct)
        if (validate(name)) {
            _product.value?.let {
                viewModelScope.launch {
                    val newProduct = it.copy(name = name, note = note)
                    updateProductUseCase.updateProduct(newProduct)
              //      addCalculatedListUseCase.addCalculatedDataList(product.id, calcData)
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

    fun deleteCalculateData(calcData: CalculatedData, product: Product) {
        viewModelScope.launch {
            deleteCalcDataUseCase.deleteCalcData(calcData.id, product.id)
        }
    }

}