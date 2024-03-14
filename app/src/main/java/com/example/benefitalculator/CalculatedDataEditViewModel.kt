package com.example.benefitalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.benefitalculator.data.BenefitCalculatorRepositoryImpl
import com.example.benefitalculator.domain.GetCalculatedListUseCase
import com.example.benefitalculator.domain.Product
import com.example.benefitalculator.ui.theme.CalculatedDataEditState


class CalculatedDataEditViewModel(
    product: Product,
    application: Application
    ) : AndroidViewModel(application) {
    private val repository = BenefitCalculatorRepositoryImpl(application)

    private val getCalculatedListUseCase = GetCalculatedListUseCase(repository)

    private val _screenState = MutableLiveData<CalculatedDataEditState>(CalculatedDataEditState.Initial)
    val screenState: LiveData<CalculatedDataEditState> = _screenState

    init {
        getCalcData(product)
    }

//    private var _calcDataListProduct: LiveData<List<CalculatedData>> = MutableLiveData<List<CalculatedData>>()
//    val calcDataListProduct: LiveData<List<CalculatedData>> = _calcDataListProduct

    private fun getCalcData(product: Product) {
        val calcDataList = getCalculatedListUseCase.getCalculatedList(product).value ?: mutableListOf()
        _screenState.value = CalculatedDataEditState.CalcData(
            product,
            calcDataList
        )
    }


}