package com.example.benefitalculator.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.benefitalculator.R
import com.example.benefitalculator.navigation.Screen

sealed class Navigation (
    val screen: Screen,
    val titleId: Int,
    val icon: ImageVector
) {
    object Home : Navigation(titleId = R.string.navigation_home, screen = Screen.HomeScreen, icon = Icons.Outlined.Home)
    object Favorites : Navigation(titleId = R.string.navigation_favorites, screen = Screen.ProductScreen, icon = Icons.Outlined.Favorite)

    object About : Navigation(titleId = R.string.navigation_about, screen = Screen.AboutScreen, icon = Icons.Outlined.Info)

}