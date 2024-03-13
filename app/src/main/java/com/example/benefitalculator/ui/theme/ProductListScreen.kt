package com.example.benefitalculator.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.benefitalculator.ProductViewModel
import com.example.benefitalculator.R
import com.example.benefitalculator.domain.Product
import com.example.benefitalculator.navigation.Screen


@Composable
fun ProductListScreen(

) {
    val viewModel: ProductViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(ProductScreenState.Initial)

    when (val screenStateCurrent = screenState.value) {
        is ProductScreenState.Products -> ProductList(screenStateCurrent.products, viewModel)
        is ProductScreenState.CalcData -> CalculatedDataListEdit(screenStateCurrent.product, screenStateCurrent.calcData)
        ProductScreenState.Initial -> { }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductList(
    productList2: LiveData<List<Product>>,
    viewModel: ProductViewModel
) {
    val productList = viewModel.productList.observeAsState(listOf())
    LazyColumn(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 70.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(productList.value, key = { it.id }) { product ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.deleteProduct(product)
            }
//            if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
//
//            }

            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier.animateItemPlacement(),
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = stringResource(R.string.product_screen_delete_calculated))
                        Icon(
                            Icons.Rounded.Delete,
                            contentDescription = stringResource(R.string.home_screen_delete_calculation)
                        )
                    }
                },
                dismissContent = {
                    ProductCard(
                        product,
                        viewModel
                    )
                }
            )
        }
    }
}