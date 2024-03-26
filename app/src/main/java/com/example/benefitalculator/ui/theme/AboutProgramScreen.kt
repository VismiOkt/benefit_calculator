package com.example.benefitalculator.ui.theme

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.benefitalculator.AboutProgramViewModel
import com.example.benefitalculator.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AboutProgramScreen() {
  //  val viewModel: AboutProgramViewModel = viewModel()
    val context = LocalContext.current
    val intentTi = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.tinkoff.ru/rm/strekalova.viktoriya8/57dMQ77598/")
        )
    }
    val intentSb = remember {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.sberbank.com/sms/pbpn?requisiteNumber=79191931344")
        )
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 65.dp, start = 8.dp, end = 8.dp, bottom = 98.dp)
    ) {
        SelectionContainer(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.about_program_screen_description))
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.ben_calc_512),
                    contentDescription = "",
                    alignment = Alignment.TopCenter
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            SelectionContainer {
                Text(text = stringResource(R.string.about_program_screen_feedback))
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { context.startActivity(intentTi) }) {
                Text(text = stringResource(R.string.about_program_screen_author_ti))
            }
            Button(onClick = { context.startActivity(intentSb) }) {
                Text(text = stringResource(R.string.about_program_screen_author_sb))
            }

//            Row {
//                Text(text = stringResource(R.string.about_program_screen_author2))
//                Spacer(modifier = Modifier.width(8.dp))
//                val text = stringResource(R.string.about_program_screen_author_card_copy)
//                Text(
//                    modifier = Modifier.combinedClickable(
//                        onClick = { },
//                        onLongClick = {
//                            viewModel.copyToClipboard(context, text)
//                        },
//                        onLongClickLabel = "text"
//                    ),
//                    text = stringResource(R.string.about_program_screen_author_card)
//                )
//            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.about_program_screen_version))

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