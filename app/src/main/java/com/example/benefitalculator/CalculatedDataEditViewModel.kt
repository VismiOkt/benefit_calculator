package com.example.benefitalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.benefitalculator.data.BenefitCalculatorRepositoryImpl
import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.GetCalculatedListUseCase
import com.example.benefitalculator.domain.Product
import com.example.benefitalculator.ui.theme.CalculatedDataEditState



class CalculatedDataEditViewModel(
    product: Product,
    application: Application
    ) : AndroidViewModel(application) {
    private val repository = BenefitCalculatorRepositoryImpl(application)

    private val getCalculatedListUseCase = GetCalculatedListUseCase(repository)

    private val calcDataList: LiveData<List<CalculatedData>> = getCalculatedListUseCase.getCalculatedList(product)
    private val initialState = CalculatedDataEditState.CalcData(product, calcDataList)

    private val _screenState = MutableLiveData<CalculatedDataEditState>(initialState)
    val screenState: LiveData<CalculatedDataEditState> = _screenState





}