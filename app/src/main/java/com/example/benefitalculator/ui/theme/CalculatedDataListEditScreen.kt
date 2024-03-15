package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.benefitalculator.CalculatedDataEditViewModel
import com.example.benefitalculator.CalculatedDataEditViewModelFactory
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.Product

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalculatedDataListEditScreen(
    product: Product,
    onBackPressed: () -> Unit
) {
    val viewModel: CalculatedDataEditViewModel = viewModel(
        factory = CalculatedDataEditViewModelFactory(product)
    )
    val screenState = viewModel.screenState.observeAsState(CalculatedDataEditState.Initial)
    val currentState = screenState.value
    if (currentState is CalculatedDataEditState.CalcData) {
        CalculatedDataListEdit(
            currentState.calcData

        )
    }
    TopAppBar(title = {
        TopAppBarCalcDataEdit(viewModel = viewModel)
    })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalculatedDataListEdit(
    calcData: LiveData<List<CalculatedData>>
) {
    val calcDataS = calcData.observeAsState(listOf())
    LazyColumn(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 70.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(calcDataS.value, key = { it.id }) { calcData ->
            val dismissState = rememberDismissState(
//                confirmValueChange = {
//                    !isLastCalcData.value
//                }
            )
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                //     viewModel.deleteCalculateData(calcData, product)
            }

            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier.animateItemPlacement(),
                directions = setOf(DismissDirection.EndToStart),
                background = {
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
                dismissContent = {
                    ProductCardCalculator2(
                        calcData,
//                        resultCalculate = { price, weight, calcData ->
//                            viewModel.calculate(
//                                price,
//                                weight,
//                                calcData
//                            )
//                        },
//                        resetErrorInputPrice = { viewModel.resetErrorInputPrice(calcData) },
//                        resetErrorInputWeight = { viewModel.resetErrorInputWeight(calcData) }
                    )
                }
            )


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCalcDataEdit(
    viewModel: CalculatedDataEditViewModel

) {
    val dialogSaveState = remember { mutableStateOf(false) }
    if (dialogSaveState.value) {
        //       DialogSaveProduct(dialogSaveState, calculationDataList.value)
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
                    //                 viewModel.resetAllCalculateData()
                }
            ) {
                Icon(Icons.Outlined.Delete, contentDescription = stringResource(R.string.home_screen_clear_all))
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
