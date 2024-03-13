package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.benefitalculator.ProductViewModel
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.CalculatedData
import com.example.benefitalculator.domain.Product

@Composable
fun DialogEditProduct(
    product: Product,
    viewModel: ProductViewModel,
    dialogState: MutableState<Boolean>

) {

    val nameProduct = rememberSaveable { mutableStateOf(viewModel.product.value?.name ?: "9") }
    val noteProduct = rememberSaveable { mutableStateOf(viewModel.product.value?.note ?: "") }



    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.editProduct(product, nameProduct.value, noteProduct.value)
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
                TextField(
                    value = nameProduct.value,
                    onValueChange = {
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