package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.Product


@Composable
fun ProductCard(
    product: Product
 //   calcData: CalculatedData,
//    resultCalculate: (String, String, CalculatedData) -> Unit,
//    resetErrorInputPrice: (CalculatedData) -> Unit,
//    resetErrorInputWeight: (CalculatedData) -> Unit
) {

    val price = rememberSaveable { mutableStateOf("") }
    val weight = rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.List, contentDescription = "")
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.Edit, contentDescription = "")
            }
            Text(text = product.name, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            Column(
               // modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.product_card_calculate_result),
                    modifier = Modifier.width(90.dp)
                )
//                val colorText = if (calcData.isBestPrice) Color.Green else Color.White
//                Text(
//                    text = calcData.resultPrice.toString(),
//                    fontSize = 20.sp,
//                    color = colorText,
//                    modifier = Modifier
//
//                )
            }
        }


    }
}