package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.benefitalculator.MainViewModel
import com.example.benefitalculator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHome (
    viewModel: MainViewModel,
    onClickSaveProduct: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.navigation_home))

        },
        navigationIcon = {
            Icon(
                Icons.Outlined.Home,
                contentDescription = "",
                modifier = Modifier.width(32.dp)
            )
        },
        actions = {
            IconButton(
                onClick = {
                    viewModel.resetAllCalculateData()
                }
            ) {
                Icon(Icons.Outlined.Delete, contentDescription = stringResource(R.string.home_screen_clear_all))
            }

            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { onClickSaveProduct() }
            ) {
                Icon(Icons.Outlined.Check, contentDescription = "")
            }

            Spacer(modifier = Modifier.width(8.dp))
        }
    )
}
