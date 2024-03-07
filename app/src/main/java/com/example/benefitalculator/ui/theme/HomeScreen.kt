package com.example.benefitalculator.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.benefitalculator.MainViewModel
import com.example.benefitalculator.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {
    val calculationDataList = viewModel.calculateData.observeAsState(listOf())
    val isLastCalcData = viewModel.isLastCalcData.observeAsState(false)
    val checkedAdd = remember { mutableStateOf(false) }
    val checkedFavorite = remember { mutableStateOf(false) }
    val dialogSaveState = remember { mutableStateOf(false) }
    if (dialogSaveState.value) {
        DialogSaveProduct(dialogSaveState, calculationDataList.value)
    }


    Scaffold(
        topBar = {
                 TopAppBarHome(
                     viewModel = viewModel,
                     onClickSaveProduct = {
                         dialogSaveState.value = true
                     }
                 )
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                IconToggleButton(checked = checkedAdd.value, onCheckedChange = {
                    checkedAdd.value = it
                }) {
                    Icon(Icons.Outlined.Add, contentDescription = "")
                }
                IconToggleButton(checked = checkedFavorite.value, onCheckedChange = {
                    checkedFavorite.value = it
                }) {
                    Icon(Icons.Outlined.Favorite, contentDescription = "")
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addNewCalculateData() }) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.home_screen_add_new_calculation)
                )
            }
        }) {

        LazyColumn(
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 70.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(calculationDataList.value, key = { it.id }) { calcData ->
                val dismissState = rememberDismissState(
                    confirmValueChange = {
                        !isLastCalcData.value
                    }
                )
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.deleteCalculateData(calcData)
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
                        ProductCardCalculator(
                            calcData,
                            resultCalculate = { price, weight, calcData ->
                                viewModel.calculate(
                                    price,
                                    weight,
                                    calcData
                                )
                            },
                            resetErrorInputPrice = { viewModel.resetErrorInputPrice(calcData) },
                            resetErrorInputWeight = { viewModel.resetErrorInputWeight(calcData) }
                        )
                    }
                )


            }
        }

    }
}
