package com.example.benefitalculator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    productListScreenContent: @Composable () -> Unit,
    aboutScreenContent: @Composable () -> Unit,
    calcDataEditScreenContent: @Composable (Int, String) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            homeScreenContent()
        }
        productScreenNavGraph(
            productListScreenContent,
            calcDataEditScreenContent
        )
        composable(
            route = Screen.AboutScreen.route
        ) {
            aboutScreenContent()
        }
    }
}