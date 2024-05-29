package com.vismiokt.benefit_calculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vismiokt.benefit_calculator.R
import com.vismiokt.benefit_calculator.domain.CalculatedData

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalculatedDataList(
    viewModel: MainViewModel,
    calculationDataList: State<List<CalculatedData>>,
    modifier: Modifier

) {
    val isLastCalcData = viewModel.isLastCalcData.observeAsState(false)
    val uiState = viewModel.uiState.collectAsState().value

    val dialogSaveState = remember { mutableStateOf(false) }
    if (dialogSaveState.value) {
        DialogSaveProduct(dialogSaveState, calculationDataList.value)
    }

    LazyColumn(
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            top = 70.dp,
            bottom = 88.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Card {
                FancyIndicatorTabs(uiState) { viewModel.switchBetweenKgAndG(it) }
            }
        }
        items(calculationDataList.value, key = { it.id }) { calcData ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    !isLastCalcData.value
                }
            )
            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                viewModel.deleteCalculateData(calcData)
            }
            SwipeToDismissBox(
                state = dismissState,
                modifier = Modifier.animateItemPlacement(),
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    CalcDataSwipeToDismiss()
                },
            ) {
                ProductCardCalculator(
                    calcData,
                    startPrice = "",
                    startWeight = "",
                    resultCalculate = { price, weight, calcData ->
                        viewModel.calculate(
                            price,
                            weight,
                            calcData
                        )
                    },
                    uiState = uiState,
                )
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            modifier = modifier.padding(end = 16.dp),
            onClick = {
                viewModel.addNewCalculateData()
            }
        ) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = stringResource(R.string.home_screen_add_new_calculation)
            )
        }
    }
    BenefitCalculatorAppBar(
        title = stringResource(R.string.navigation_home),
        icon = Icons.Outlined.Home,
        onBackPressed = {},
        actionBar = {
            ActionBarTwoElements(
                iconActionOne = Icons.Outlined.Delete,
                iconActionTwo = Icons.Outlined.Check,
                onActionOnePressed = { viewModel.resetAllCalculateData() },
                onActionTwoPressed = { dialogSaveState.value = true }
            )
        }
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FancyIndicatorTabs(
    state: UiState,
    onTabPressed: (Int) -> Unit
) {
    val titles = listOf(
        stringResource(R.string.home_screen_kilogram),
        stringResource(R.string.home_screen_gram)
    )

    Column {
        SecondaryTabRow(
            selectedTabIndex = state.indicatorTabsState,
            indicator = {
                FancyIndicator(
                    MaterialTheme.colorScheme.primary,
                    Modifier.tabIndicatorOffset(state.indicatorTabsState)
                )
            }
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state.indicatorTabsState == index,
                    onClick = { onTabPressed(index) },
                    text = { Text(title) }
                )
            }
        }
//        Text(
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            text = "Fancy indicator tab ${state + 1} selected",
//            style = MaterialTheme.typography.bodyLarge
//        )
    }
}


@Composable
fun FancyIndicator(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(5.dp)
            .fillMaxSize()
            .border(BorderStroke(2.dp, color), RoundedCornerShape(5.dp))
    )
}




