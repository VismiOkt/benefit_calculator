package com.vismiokt.benefit_calculator.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vismiokt.benefit_calculator.R
import com.vismiokt.benefit_calculator.domain.Product

@Composable
fun ProductListScreen(
    onCalcDataEditListener: (Product) -> Unit

) {
    val viewModel: ProductViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(ProductScreenState.Initial)
    val topAppBarSearch = rememberSaveable {
        mutableStateOf(false)

    }
    val errorInputName = viewModel.errorInputName.observeAsState(false)
    val dialogEditState = remember { mutableStateOf(false) }
    val productListSearch = viewModel.productListSearch.observeAsState(listOf())

    when (val screenStateCurrent = screenState.value) {
        is ProductScreenState.Products -> {
            ProductList(
                productList = screenStateCurrent.products,
                onCalcDataEditListener = onCalcDataEditListener,
                errorInputName = errorInputName,
                resetErrorInputName = { viewModel.resetErrorInputName() },
                onSavePressed = { product, nameProduct, noteProduct ->
                    viewModel.editProduct(product, nameProduct, noteProduct)
                    topAppBarSearch.value = false
                    if (!errorInputName.value) dialogEditState.value = false
                },
                dialogEditState = dialogEditState,
                onEditPressed = {
                    viewModel.getProduct(productId = it)
                    dialogEditState.value = true
                },
                onDeletePressed = { viewModel.deleteProduct(it) }
            )
        }
        ProductScreenState.Initial -> {}
    }
    if (topAppBarSearch.value) {
        TopAppBarSearch(
            topAppBarSearch = topAppBarSearch,
            onCalcDataEditListener = onCalcDataEditListener,
            dialogEditState = dialogEditState,
            errorInputName = errorInputName,
            onEditPressed = {
                viewModel.getProduct(productId = it)
                dialogEditState.value = true
            },
            onSavePressed = { product, nameProduct, noteProduct ->
                viewModel.editProduct(product, nameProduct, noteProduct)
                topAppBarSearch.value = false
                if (!errorInputName.value) dialogEditState.value = false
            },
            resetErrorInputName = { viewModel.resetErrorInputName() },
            productListSearch = productListSearch,
            onSearchProduct = { viewModel.searchProduct(it) }

        )
    } else BenefitCalculatorAppBar(
        title = stringResource(R.string.navigation_favorites),
        icon = Icons.Outlined.FavoriteBorder,
        onBackPressed = {},
        actionBar = {
            ActionBarOneElement(iconActionOne = Icons.Default.Search) {
                viewModel.initialSearchProductList()
                topAppBarSearch.value = true
            }
        }

    )

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductList(
    productList: LiveData<List<Product>>,
    onCalcDataEditListener: (Product) -> Unit,
    errorInputName: State<Boolean>,
    resetErrorInputName: () -> Unit,
    onSavePressed: (Product, String, String) -> Unit,
    dialogEditState: MutableState<Boolean>,
    onEditPressed: (Int) -> Unit,
    onDeletePressed: (Product) -> Unit
) {
    val productListS = productList.observeAsState(listOf())
    LazyColumn(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 70.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(productListS.value, key = { it.id }) { product ->
            val dismissState = rememberSwipeToDismissBoxState()
            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                onDeletePressed(product)
            }
            SwipeToDismissBox(
                state = dismissState,
                modifier = Modifier.animateItemPlacement(),
                enableDismissFromEndToStart = true,
                backgroundContent = {
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
                }
            ) {
                ProductCard(
                    product = product,
                    onCalcDataEditListener = onCalcDataEditListener,
                    errorInputName = errorInputName,
                    resetErrorInputName = resetErrorInputName,
                    onSavePressed = onSavePressed,
                    dialogEditState = dialogEditState,
                    onEditPressed = { onEditPressed(it) }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSearch(
    topAppBarSearch: MutableState<Boolean>,
    onCalcDataEditListener: (Product) -> Unit,
    errorInputName: State<Boolean>,
    resetErrorInputName: () -> Unit,
    onSavePressed: (Product, String, String) -> Unit,
    dialogEditState: MutableState<Boolean>,
    onEditPressed: (Int) -> Unit,
    productListSearch: State<List<Product>>,
    onSearchProduct: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        query = text,
        onQueryChange = {
            onSearchProduct(it)
            text = it
        },
        onSearch = {
            onSearchProduct(it)
        },
        active = true,
        onActiveChange = { active = it },
        placeholder = { Text(stringResource(R.string.product_screen_search)) },
        leadingIcon = {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable {
                    topAppBarSearch.value = false
                }
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        text = ""
                        onSearchProduct("")
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productListSearch.value, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    onCalcDataEditListener = onCalcDataEditListener,
                    errorInputName = errorInputName,
                    resetErrorInputName = resetErrorInputName,
                    onSavePressed = onSavePressed,
                    dialogEditState = dialogEditState,
                    onEditPressed = { onEditPressed(it) }
                )

            }
        }

    }

}

