package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.benefitalculator.ProductViewModel
import com.example.benefitalculator.domain.Product

@Composable
fun ProductCard(
    product: Product,
    viewModel: ProductViewModel,
    onCalcDataEditListener: (Product) -> Unit
) {
    val isExpanded = rememberSaveable {
        mutableStateOf(false)
    }
    val dialogEditState = remember { mutableStateOf(false) }
    if (dialogEditState.value) {
        DialogEditProduct(product, viewModel, dialogEditState)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded.value = !isExpanded.value
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row {
                    Text(
                        text = product.name,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        onCalcDataEditListener(product)
                    }) {
                        Icon(Icons.Outlined.List, contentDescription = "")
                    }
                    IconButton(onClick = {
                        viewModel.getProduct(productId = product.id)
                        dialogEditState.value = true
                    }) {
                        Icon(Icons.Outlined.Edit, contentDescription = "")
                    }
                }
                Row {
                    Text(
                        text = product.note, modifier = Modifier.clickable {
                            isExpanded.value = !isExpanded.value
                        },
                        maxLines = if (isExpanded.value) 100 else 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
        }
    }
}