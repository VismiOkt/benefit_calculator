package com.example.benefitalculator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.productScreenNavGraph(
    productListScreenContent: @Composable () -> Unit,
    calcDataEditScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.ProductListScreen.route,
        route = Screen.ProductScreen.route
    ) {
        composable(
            route = Screen.ProductListScreen.route
        ) {
            productListScreenContent()
        }
        composable(
            route = Screen.CalcDataEditScreen.route
        ) {
            calcDataEditScreenContent()
        }
    }

}