package com.example.benefitalculator.ui.theme


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.CalculatedData

@Composable
fun ProductCardCalculator(
    calcData: CalculatedData
) {
    val price = rememberSaveable { mutableStateOf("") }
    val weight = rememberSaveable { mutableStateOf("") }


    Card (modifier = Modifier.fillMaxWidth()) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = price.value,
                onValueChange = { price.value = it },
                label = { Text(stringResource(R.string.product_card_calculate_price_1)) },
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = weight.value,
                onValueChange = { weight.value = it },
                label = { Text(stringResource(R.string.product_card_calculate_weight_1)) },
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column (modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.product_card_calculate_result)
                )
                Text(
                    text = calcData.resultPrice.toString(),
                    modifier = Modifier

                )
            }
        }



    }
}