package com.example.benefitalculator.ui.theme


import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.benefitalculator.MainViewModel
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.CalculatedData

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductCardCalculator(
    calcData: CalculatedData,
    errorInputPrice: () -> Boolean,
    errorInputWeight: () -> Boolean,
    resultCalculate: (String, String, CalculatedData) -> Unit,
    resetErrorInputPrice: () -> Unit,
    resetErrorInputWeight: () -> Unit
) {

    val price = rememberSaveable { mutableStateOf("") }
    val weight = rememberSaveable { mutableStateOf("") }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = price.value,
                onValueChange = {
                    resetErrorInputPrice()
                    price.value = it
                },
                isError = errorInputPrice(),
                label = { Text(stringResource(R.string.product_card_calculate_price_1)) },
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    resultCalculate(price.value, weight.value, calcData)
                })
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = weight.value,
                onValueChange = {
                    resetErrorInputWeight()
                    weight.value = it
                },
                label = { Text(stringResource(R.string.product_card_calculate_weight_1)) },
                isError = errorInputWeight(),
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    resultCalculate(price.value, weight.value, calcData)
                })
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.product_card_calculate_result)
                )
                val colorText = if (calcData.isBestPrice) Color.Green else Color.White
                Text(
                    text = calcData.resultPrice.toString(),
                    fontSize = 20.sp,
                    color = colorText,
                    modifier = Modifier

                )
            }
        }


    }
}