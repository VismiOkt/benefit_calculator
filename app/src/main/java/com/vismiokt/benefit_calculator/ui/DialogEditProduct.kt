package com.vismiokt.benefit_calculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.vismiokt.benefit_calculator.R
import com.vismiokt.benefit_calculator.domain.Product

@Composable
fun DialogEditProduct(
    product: Product,
 //   viewModel: ProductViewModel,
    dialogState: MutableState<Boolean>,
 //   topAppBarSearch: MutableState<Boolean>,
    errorInputName: State<Boolean>,
    resetErrorInputName: () -> Unit,
    onSavePressed: (String, String) -> Unit,

    ) {
    val nameProduct = rememberSaveable { mutableStateOf(product.name) }
    val noteProduct = rememberSaveable { mutableStateOf(product.note) }
 //   val errorInputName = viewModel.errorInputName.observeAsState(false)

    val maxName = 100
    val maxNote = 350

    AlertDialog(
        onDismissRequest = { dialogState.value = false },
        properties = DialogProperties(decorFitsSystemWindows = false),
        modifier = Modifier.imePadding().verticalScroll(rememberScrollState()),
        confirmButton = {
            TextButton(
                onClick = { onSavePressed(nameProduct.value, noteProduct.value) }
//                    viewModel.editProduct(product, nameProduct.value, noteProduct.value)
//                    topAppBarSearch.value = false
//                    if (!errorInputName.value) dialogState.value = false

            ) {
                Text(text = stringResource(R.string.dialog_save_product_save), modifier = Modifier.imePadding())
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
                    isError = errorInputName.value,
                    singleLine = true,
                    onValueChange = {
                        if (it.length <= maxName) nameProduct.value = it
                        resetErrorInputName()
                    },
                    label = {
                        Text(text = stringResource(R.string.dialog_save_product_name))
                    },
                    supportingText = {
                        Text(
                            text = "${nameProduct.value.length} / $maxName",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                        )
                    }
                )
                TextField(
                    value = noteProduct.value,
                    onValueChange = {
                        if (it.length <= maxName) noteProduct.value = it
                    },
                    supportingText = {
                        Text(
                            text = "${noteProduct.value.length} / $maxNote",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                        )
                    },
                    label = {
                        Text(text = stringResource(R.string.dialog_save_product_note))
                    }
                )
            }
        }
    )
}