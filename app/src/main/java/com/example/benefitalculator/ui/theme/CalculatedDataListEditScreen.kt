package com.example.benefitalculator.ui.theme

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
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.benefitalculator.CalculatedDataEditViewModel
import com.example.benefitalculator.CalculatedDataEditViewModelFactory
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.CalculatedData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatedDataListEditScreen(
    productId: Int,
    productName: String,
    onBackPressed: () -> Unit
) {
    val viewModel: CalculatedDataEditViewModel = viewModel(
        factory = CalculatedDataEditViewModelFactory(productId)
    )
    val screenState = viewModel.screenState.observeAsState(CalculatedDataEditState.Initial)
    val currentState = screenState.value
    if (currentState is CalculatedDataEditState.CalcData) {
        viewModel.initialList(productId)
        CalculatedDataListEdit(
            currentState.calcData,
            viewModel

        )
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            modifier = Modifier.padding(bottom = 95.dp, end = 16.dp),
            onClick = {
                viewModel.addNewCalculateData()
            }) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = stringResource(R.string.home_screen_add_new_calculation)
            )
        }
    }
    TopAppBar(title = {
        TopAppBarCalcDataEdit(productId, productName, onBackPressed)
    })

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalculatedDataListEdit(
    calcDataList: LiveData<List<CalculatedData>>,
    viewModel: CalculatedDataEditViewModel
) {
    val calcDataLi = calcDataList.observeAsState(listOf())
    val isLastCalcData = viewModel.isLastCalcData.observeAsState(false)

    LazyColumn(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 70.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(calcDataLi.value, key = { it.id }) { calcData ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (isLastCalcData.value) false
                    else {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.deleteCalculateData(calcData)
                            true
                        } else false
                    }
                }
            )
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
                }
            ) {
                ProductCardCalculator2(
                    calcData,
                    resultCalculate = { price, weight, calcData ->
                        viewModel.calculate(
                            price,
                            weight,
                            calcData
                        )
                    }
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCalcDataEdit(
    productId: Int,
    productName: String,
    onBackPressed: () -> Unit

) {
    val dialogSaveState = remember { mutableStateOf(false) }
    if (dialogSaveState.value) {
        DialogSaveCalculateData(dialogSaveState, productId)
    }
    TopAppBar(
        title = {
            Text(
                text = productName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
                )

        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = ""
                )
            }

        },
        actions = {
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
