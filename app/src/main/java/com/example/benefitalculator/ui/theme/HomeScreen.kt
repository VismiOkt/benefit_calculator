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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.benefitalculator.MainViewModel
import com.example.benefitalculator.R
import com.example.benefitalculator.navigation.AppNavGraph
import com.example.benefitalculator.navigation.NavigationState
import com.example.benefitalculator.navigation.Screen
import com.example.benefitalculator.navigation.rememberNavigationState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {
    val calculationDataList = viewModel.calculateData.observeAsState(listOf())

    val dialogSaveState = remember { mutableStateOf(false) }
    if (dialogSaveState.value) {
        DialogSaveProduct(dialogSaveState, calculationDataList.value)
    }
    val navigationState = rememberNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentRout = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            when(currentRout) {
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
            when(currentRout) {
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
                CalculatedDataListScreen(
                    viewModel = viewModel,
                    calculationDataList = calculationDataList
                )
            },
            productScreenContent = {
                ProductListScreen()
            },
            aboutScreenContent = {
                Text(text = "About program")
            }
        )


    }
}
