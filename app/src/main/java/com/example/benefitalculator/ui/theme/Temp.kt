package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.border
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp

@Composable
fun Temp() {

    val focusRequester = remember { FocusRequester() }
    var value by remember { mutableStateOf("apple") }
    var borderColor by remember { mutableStateOf(Transparent) }
    TextField(
        value = value,
        onValueChange = {
            value = it.apply {
                if (length > 5) focusRequester.captureFocus() else focusRequester.freeFocus()
            }
        },
        modifier = Modifier
            .border(2.dp, borderColor)
            .focusRequester(focusRequester)
            .onFocusChanged { borderColor = if (it.isCaptured) Red else Transparent }
    )
}