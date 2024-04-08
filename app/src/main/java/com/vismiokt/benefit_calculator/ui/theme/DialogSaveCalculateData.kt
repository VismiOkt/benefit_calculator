package com.vismiokt.benefit_calculator.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vismiokt.benefit_calculator.CalculatedDataEditViewModel
import com.vismiokt.benefit_calculator.R

@Composable
fun DialogSaveCalculateData(
    dialogState: MutableState<Boolean>,
    productId: Int
) {
    val viewModel: CalculatedDataEditViewModel = viewModel()

    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        properties = DialogProperties(decorFitsSystemWindows = false),
        modifier = Modifier.imePadding().verticalScroll(rememberScrollState()),
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.saveChangesCalculationData(productId)
                    dialogState.value = false
                }
            ) {
                Text(text = stringResource(R.string.dialog_save_product_save))
            }
        },
        dismissButton = {
            TextButton(onClick = { dialogState.value = false }) {
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