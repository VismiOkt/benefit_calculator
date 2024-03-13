package com.example.benefitalculator.navigation

import androidx.compose.ui.res.stringResource
import com.example.benefitalculator.R

sealed class Screen(
    val route: String
) {
    data object HomeScreen : Screen(ROUTE_HOME_SCREEN)

    data object ProductScreen : Screen(ROUTE_PRODUCT_SCREEN)

    data object AboutScreen : Screen(ROUTE_ABOUT_SCREEN)

    companion object {
        const val ROUTE_HOME_SCREEN = "home_screen"
        const val ROUTE_PRODUCT_SCREEN = "product_screen"
        const val ROUTE_ABOUT_SCREEN = "about_screen"
    }

}