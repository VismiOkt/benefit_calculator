package com.example.benefitalculator.ui.theme

import com.example.benefitalculator.R

sealed class Navigation (
    val titleId: Int
) {
    object Home : Navigation(titleId = R.string.navigation_home)
    object Favorites : Navigation(titleId = R.string.navigation_favorites)

}