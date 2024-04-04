package com.example.benefitalculator.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.benefitalculator.MainViewModel
import com.example.benefitalculator.navigation.AppNavGraph
import com.example.benefitalculator.navigation.NavigationState
import com.example.benefitalculator.navigation.rememberNavigationState

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
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
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

