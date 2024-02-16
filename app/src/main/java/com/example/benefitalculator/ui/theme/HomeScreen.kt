package com.example.benefitalculator.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.benefitalculator.MainViewModel
import com.example.benefitalculator.domain.CalculatedData

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (
    viewModel: MainViewModel
) {
    val calculationDataList: List<CalculatedData> = viewModel.calculateData.value ?: mutableListOf()


    Scaffold (bottomBar = {
        BottomAppBar {

        }
    }) {
        LazyColumn(content = {
            items (calculationDataList, key = { it.id }) { ProductCardCalculator(it) }
        })

    }
}
