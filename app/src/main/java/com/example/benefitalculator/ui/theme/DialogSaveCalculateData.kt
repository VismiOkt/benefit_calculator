package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.benefitalculator.CalculatedDataEditViewModel
import com.example.benefitalculator.R

@Composable
fun DialogSaveCalculateData(
    dialogState: MutableState<Boolean>,
    productId: Int
) {
    val viewModel: CalculatedDataEditViewModel = viewModel()

    AlertDialog(
        onDismissRequest = { dialogState.value = false },
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