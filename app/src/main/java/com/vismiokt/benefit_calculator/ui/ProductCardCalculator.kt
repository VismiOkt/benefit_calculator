package com.vismiokt.benefit_calculator.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vismiokt.benefit_calculator.R
import com.vismiokt.benefit_calculator.domain.CalculatedData
import com.vismiokt.benefit_calculator.ui.theme.md_best_price

@Composable
fun ProductCardCalculator(
    calcData: CalculatedData,
    startPrice: String,
    startWeight: String,
    resultCalculate: (String, String, CalculatedData) -> Unit,
    uiState: UiState,
) {
    val price = rememberSaveable { mutableStateOf(startPrice) }
    val weight = rememberSaveable { mutableStateOf(startWeight) }


    resultCalculate(price.value, weight.value, calcData)

    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
                onValueChange = { price.value = it },
                isError = calcData.errorInputPrice,
                label = { Text(stringResource(R.string.product_card_calculate_price_1)) },
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = weight.value,
                onValueChange = { weight.value = it },
                label = {
                    Text(
                        stringResource(
                            R.string.product_card_calculate_weight_1,
                            if (uiState.indicatorTabsState == 0) stringResource(R.string.home_screen_kg) else stringResource(
                                R.string.home_screen_g
                            )
                        )
                    )
                },
                isError = calcData.errorInputWeight,
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
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