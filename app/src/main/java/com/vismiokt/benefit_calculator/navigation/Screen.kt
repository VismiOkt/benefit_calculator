package com.vismiokt.benefit_calculator.navigation

import com.vismiokt.benefit_calculator.domain.Product

sealed class Screen(
    val route: String
) {
    data object HomeScreen : Screen(ROUTE_HOME_SCREEN)

    data object ProductScreen : Screen(ROUTE_PRODUCT_SCREEN)

    data object ProductListScreen : Screen(ROUTE_PRODUCT_LIST_SCREEN)

    data object CalcDataEditScreen : Screen(ROUTE_CALC_DATA_EDIT_SCREEN) {
        private const val ROUTE_FOR_CALC_DATA_EDIT = "calc_data_edit_screen"
        fun getProductIdAndName(product: Product): String {
            return "$ROUTE_FOR_CALC_DATA_EDIT/${product.id}/${product.name}"
        }
    }

    data object AboutScreen : Screen(ROUTE_ABOUT_SCREEN)

    companion object {

        const val ROUTE_HOME_SCREEN = "home_screen"
        const val ROUTE_PRODUCT_SCREEN = "product_screen"
        const val ROUTE_PRODUCT_LIST_SCREEN = "product_list_screen"
        const val ROUTE_CALC_DATA_EDIT_SCREEN = "calc_data_edit_screen/{product_id}/{product_name}"
        const val ROUTE_ABOUT_SCREEN = "about_screen"
    }

}