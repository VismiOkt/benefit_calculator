package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.benefitalculator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutProgramScreen() {
    Card (modifier = Modifier
        .fillMaxSize()
        .padding(top = 65.dp, start = 8.dp, end = 8.dp, bottom = 98.dp)) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.about_program_screen_description))
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.ben_calc_512),
                contentDescription = "",
                alignment = Alignment.TopCenter)


        }
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
            ){
            Text(text = stringResource(R.string.about_program_screen_feedback))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.about_program_screen_author))
            Text(text = stringResource(R.string.about_program_screen_author2))
        }

    }
    TopAppBar(title = {
        TopAppBarAboutProgram()
    })


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarAboutProgram(
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.about_program_screen_title))
        },
        navigationIcon = {
            Icon(
                Icons.Outlined.Info,
                contentDescription = "",
                modifier = Modifier.width(32.dp)
            )
        }
    )
}