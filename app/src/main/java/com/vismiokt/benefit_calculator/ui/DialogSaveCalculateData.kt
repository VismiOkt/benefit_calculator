package com.vismiokt.benefit_calculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.vismiokt.benefit_calculator.R

@Composable
fun DialogSaveCalculateData(
    productId: Int,
    onSaveCalcDataPressed: (Int) -> Unit,
    onCancelPressed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancelPressed() },
        properties = DialogProperties(decorFitsSystemWindows = false),
        modifier = Modifier.imePadding().verticalScroll(rememberScrollState()),
        confirmButton = {
            TextButton(
                onClick = { onSaveCalcDataPressed(productId) }
            ) {
                Text(text = stringResource(R.string.dialog_save_product_save))
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancelPressed() }) {
                Text(text = stringResource(R.string.dialog_save_product_cancel))
            }
        },
        title = {
            Column {
                Text(text = stringResource(R.string.dialog_edit_product_title_save))
            }
        }
    )
}