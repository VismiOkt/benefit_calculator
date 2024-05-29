package com.vismiokt.benefit_calculator.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vismiokt.benefit_calculator.R
import com.vismiokt.benefit_calculator.domain.CalculatedData

@Composable
fun CalculatedDataListEditScreen(
    productId: Int,
    productName: String,
    onBackPressed: () -> Unit,
    modifier: Modifier
) {
    val viewModel: CalculatedDataEditViewModel = viewModel(
        factory = CalculatedDataEditViewModelFactory(productId)
    )
    val screenState = viewModel.screenState.observeAsState(CalculatedDataEditState.Initial)
    val currentState = screenState.value


    val isLastCalcData = viewModel.isLastCalcData.observeAsState(false)
    val uiState = viewModel.uiState.collectAsState().value

    val dialogSaveState = remember { mutableStateOf(false) }

    val calcDataLi = viewModel.calculatedData.observeAsState(listOf())


    if (currentState is CalculatedDataEditState.CalcData) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                modifier = modifier.padding(end = 16.dp),
                onClick = {
                    viewModel.addNewCalculateData()
                }) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.home_screen_add_new_calculation)
                )
            }
        }
        CalculatedDataListEdit(
            calcDataLi,
            isLastCalcData = isLastCalcData,
            uiState = uiState,
            deleteCalculateData = { viewModel.deleteCalculateData(it) },
            resultCalculate = { price, weight, calcData ->
                viewModel.calculate(
                    price,
                    weight,
                    calcData
                )
            },
        )

    }

    BenefitCalculatorAppBar(
        title = productName,
        icon = Icons.AutoMirrored.Outlined.ArrowBack,
        onBackPressed = onBackPressed,
        actionBar = {
            ActionBarOneElement(iconActionOne = Icons.Outlined.Check) {
                dialogSaveState.value = true
            }
        })
    if (dialogSaveState.value) {
        DialogSaveCalculateData(
            productId,
            onCancelPressed = { dialogSaveState.value = false },
            onSaveCalcDataPressed = {
                viewModel.saveChangesCalculationData(productId)
                dialogSaveState.value = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalculatedDataListEdit(
    calcDataLi: State<List<CalculatedData>>,
    isLastCalcData: State<Boolean>,
    uiState: UiState,
    deleteCalculateData: (CalculatedData) -> Unit,
    resultCalculate: (String, String, CalculatedData) -> Unit,

) {
    LazyColumn(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 70.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(calcDataLi.value, key = { it.id }) { calcData ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    !isLastCalcData.value
                }
            )
            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                deleteCalculateData(calcData)
            }
            SwipeToDismissBox(
                state = dismissState,
                modifier = Modifier.animateItemPlacement(),
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    CalcDataSwipeToDismiss()
                }
            ) {
                ProductCardCalculator(
                    calcData,
                    startPrice = calcData.price.toString(),
                    startWeight = calcData.weight.toString(),
                    resultCalculate = resultCalculate,
                    uiState = uiState,
                    )
            }
        }
    }
}

