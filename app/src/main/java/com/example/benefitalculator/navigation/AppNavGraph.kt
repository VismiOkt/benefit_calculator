package com.example.benefitalculator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    productScreenContent: @Composable () -> Unit,
    aboutScreenContent: @Composable () -> Unit
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
        composable(
            route = Screen.ProductScreen.route
        ) {
            productScreenContent()
        }
        composable(
            route = Screen.AboutScreen.route
        ) {
            aboutScreenContent()
        }
    }
}