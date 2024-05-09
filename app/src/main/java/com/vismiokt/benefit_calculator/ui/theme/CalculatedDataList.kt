package com.vismiokt.benefit_calculator.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vismiokt.benefit_calculator.MainViewModel
import com.vismiokt.benefit_calculator.R
import com.vismiokt.benefit_calculator.domain.CalculatedData

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalculatedDataList(
    viewModel: MainViewModel,
    calculationDataList: State<List<CalculatedData>>,
    modifier: Modifier

) {
    val isLastCalcData = viewModel.isLastCalcData.observeAsState(false)

    LazyColumn(
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            top = 70.dp,
            bottom = 88.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
                backgroundContent = {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = stringResource(R.string.home_screen_delete_calculated))
                        Icon(
                            Icons.Rounded.Delete,
                            contentDescription = stringResource(R.string.home_screen_delete_calculation)
                        )
                    }
                },

            ) {
                ProductCardCalculator(
                    calcData,
                    resultCalculate = { price, weight, calcData ->
                        viewModel.calculate(
                            price,
                            weight,
                            calcData
                        )
                    },
                    startPrice = "",
                    startWeight = ""
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
    TopAppBarHome(
        viewModel = viewModel,
        calculationDataList
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHome(
    viewModel: MainViewModel,
    calculationDataList: State<List<CalculatedData>>,
) {
    val dialogSaveState = remember { mutableStateOf(false) }
    if (dialogSaveState.value) {
        DialogSaveProduct(dialogSaveState, calculationDataList.value)
    }
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.navigation_home))

        },
        navigationIcon = {
            Icon(
                Icons.Outlined.Home,
                contentDescription = "",
                modifier = Modifier.width(32.dp)
            )
        },
        actions = {
            IconButton(
                onClick = {
                    viewModel.resetAllCalculateData()
                }
            ) {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = stringResource(R.string.home_screen_clear_all)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    dialogSaveState.value = true
                }
            ) {
                Icon(Icons.Outlined.Check, contentDescription = "")
            }

            Spacer(modifier = Modifier.width(8.dp))
        }
    )
}