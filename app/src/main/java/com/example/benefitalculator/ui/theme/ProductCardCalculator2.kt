package com.example.benefitalculator.ui.theme


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.CalculatedData

@Composable
fun ProductCardCalculator2(
    calcData: CalculatedData,
    resultCalculate: (String, String, CalculatedData) -> Unit
) {

    val price = rememberSaveable { mutableStateOf(calcData.price.toString()) }
    val weight = rememberSaveable { mutableStateOf(calcData.weight.toString()) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (!it.isFocused) {
                    resultCalculate(price.value, weight.value, calcData)
                }
            },
        colors = if (calcData.isBestPrice) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer) else CardDefaults.cardColors()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = price.value,
                onValueChange = {
                    price.value = it
                },
                isError = calcData.errorInputPrice,
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
                    weight.value = it
                },
                label = { Text(stringResource(R.string.product_card_calculate_weight_1)) },
                isError = calcData.errorInputWeight,
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
                val colorText = if (calcData.isBestPrice) md_best_price else Color.Unspecified
                Text(
                    text = calcData.resultPrice.toString(),
                    fontSize = 20.sp,
                    color = colorText,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold

                )
            }
        }
    }
}