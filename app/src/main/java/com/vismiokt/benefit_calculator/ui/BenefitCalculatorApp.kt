package com.vismiokt.benefit_calculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vismiokt.benefit_calculator.navigation.AppNavGraph
import com.vismiokt.benefit_calculator.navigation.NavigationState
import com.vismiokt.benefit_calculator.navigation.rememberNavigationState

@Composable
fun BenefitCalculatorApp(
    windowSize: WindowSizeClass
) {
    val navigationState = rememberNavigationState()

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            BenefitCalculatorAppPortrait(navigationState)
        }
        WindowWidthSizeClass.Expanded, WindowWidthSizeClass.Medium -> {
            BenefitCalculatorAppLandscape(navigationState)
        }
        else -> BenefitCalculatorAppPortrait(navigationState)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BenefitCalculatorAppPortrait(
    navigationState: NavigationState
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navigationState) }
    ) {
        HomeScreen(navigationState, Modifier.padding(bottom = 95.dp))
    }
}

@Composable
fun BenefitCalculatorAppLandscape(
    navigationState: NavigationState
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Row {
            AppNavigationRail(navigationState)
            HomeScreen(navigationState, Modifier.padding(bottom = 16.dp))
        }
    }


}

@Composable
fun AppNavigationRail(
    navigationState: NavigationState
) {
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    NavigationRail {
        Surface (color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val items = listOf(
                    Navigation.Home,
                    Navigation.Favorites,
                    Navigation.About
                )
                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false
                    NavigationRailItem(
                        selected = selected,
                        label = {
                            Text(text = stringResource(item.titleId))
                        },
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
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
        }

    }
}

@Composable
fun BottomNavigationBar(
    navigationState: NavigationState
) {
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        val items = listOf(
            Navigation.Home,
            Navigation.Favorites,
            Navigation.About
        )
        items.forEach { item ->
            val selected = navBackStackEntry?.destination?.hierarchy?.any {
                it.route == item.screen.route
            } ?: false
            NavigationBarItem(
                selected = selected,
                label = {
                    Text(text = stringResource(item.titleId))
                },
                onClick = {
                    if (!selected) {
                        navigationState.navigateTo(item.screen.route)
                    }
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
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(
    navigationState: NavigationState,
    modifier: Modifier
) {
    val viewModel: MainViewModel = viewModel()
    val calculationDataList = viewModel.calculateData.observeAsState(listOf())

    AppNavGraph(
        navHostController = navigationState.navHostController,
        homeScreenContent = {
            CalculatedDataList(
                viewModel = viewModel,
                calculationDataList = calculationDataList,
                modifier
            )
        },
        productListScreenContent = {
            ProductListScreen(onCalcDataEditListener = {
                navigationState.navigateToCalcDataEdit(it)
            })
        },
        calcDataEditScreenContent = { productId, productName ->
            CalculatedDataListEditScreen(
                productId = productId,
                productName = productName,
                onBackPressed = {
                    navigationState.navHostController.popBackStack()
                },
                modifier
            )
        },
        aboutScreenContent = {
            AboutProgramScreen(modifier)

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenefitCalculatorAppBar (
    title: String,
    icon: ImageVector,
    onBackPressed: () -> Unit,
    actionBar: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(
                    icon,
                    contentDescription = null
                )
            }
        },
        actions = actionBar
    )
}

@Composable
fun ActionBarTwoElements(
    iconActionOne: ImageVector,
    iconActionTwo: ImageVector,
    onActionOnePressed: () -> Unit,
    onActionTwoPressed: () -> Unit,
) {
    IconButton(
        onClick = onActionOnePressed
    ) {
        Icon(
            iconActionOne,
            contentDescription = null
        )
    }
    Spacer(modifier = Modifier.width(8.dp))
    IconButton(
        onClick = onActionTwoPressed
    ) {
        Icon(iconActionTwo, contentDescription = "")
    }
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
fun ActionBarOneElement(
    iconActionOne: ImageVector,
    onActionOnePressed: () -> Unit
) {
    IconButton(
        onClick = onActionOnePressed
    ) {
        Icon(
            iconActionOne,
            contentDescription = null
        )
    }
    Spacer(modifier = Modifier.width(8.dp))
}


