package com.example.benefitalculator.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.benefitalculator.MainViewModel
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.Product
import com.example.benefitalculator.navigation.AppNavGraph
import com.example.benefitalculator.navigation.Screen
import com.example.benefitalculator.navigation.rememberNavigationState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {
    val calculationDataList = viewModel.calculateData.observeAsState(listOf())
    val calcDataEditToProductList: MutableState<Product?> = remember {
        mutableStateOf(null)
    }

    val dialogSaveState = remember { mutableStateOf(false) }
    if (dialogSaveState.value) {
        DialogSaveProduct(dialogSaveState, calculationDataList.value)
    }
    val navigationState = rememberNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentRout = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            when (currentRout) {
                Navigation.Home.screen.route -> TopAppBarHome(
                    viewModel = viewModel,
                    onClickSaveProduct = {
                        dialogSaveState.value = true
                    }
                )

                Navigation.Favorites.screen.route -> TopAppBarFavorites(viewModel = viewModel)
            }
        },

        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                val items = listOf(Navigation.Home, Navigation.Favorites, Navigation.About)
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRout == item.screen.route,
                        label = {
                            Text(text = stringResource(item.titleId))
                        },
                        onClick = {
                            navigationState.navigateTo(item.screen.route)
                        },
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = stringResource(item.titleId)
                            )
                        }
                    )

                }
            }
        },
        floatingActionButton = {
            when (currentRout) {
                Navigation.Home.screen.route -> FloatingActionButton(onClick = { viewModel.addNewCalculateData() }) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.home_screen_add_new_calculation)
                    )
                }
            }

        }) {
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = {
                CalculatedDataList(
                    viewModel = viewModel,
                    calculationDataList = calculationDataList
                )
            },
            productListScreenContent = {
                ProductListScreen(onCalcDataEditListener = {
                    calcDataEditToProductList.value = it
                    navigationState.navigateTo(Screen.CalcDataEditScreen.route)
                })
            },
            calcDataEditScreenContent = {
                CalculatedDataListEdit(
                    product = calcDataEditToProductList.value!!,
                    onBackPressed = {
                        calcDataEditToProductList.value = null
                    }
                )
            },
            aboutScreenContent = {
                Text(text = "About program")
            }
        )


    }
}
