package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.benefitalculator.ProductViewModel
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.CalculatedData

@Composable
fun DialogSaveProduct(
    dialogState: MutableState<Boolean>,
    calcData: List<CalculatedData>
) {
    val viewModel: ProductViewModel = viewModel()
    val nameProduct = rememberSaveable { mutableStateOf("") }
    val noteProduct = rememberSaveable { mutableStateOf("") }
    val errorInputName = viewModel.errorInputName.observeAsState(false)

    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.addProduct(nameProduct.value, noteProduct.value, calcData)
                    if(!errorInputName.value) dialogState.value = false
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
                Text(text = stringResource(R.string.dialog_save_product_title_save))
                TextField(
                    value = nameProduct.value,
                    isError = errorInputName.value,
                    onValueChange = {
                        viewModel.resetErrorInputName()
                        nameProduct.value = it
                    },
                    label = {
                        Text(text = stringResource(R.string.dialog_save_product_name))
                    }
                )
                TextField(value = noteProduct.value, onValueChange = {
                    noteProduct.value = it
                },
                    label = {
                        Text(text = stringResource(R.string.dialog_save_product_note))
                    }
                )
            }
        }
    )
}