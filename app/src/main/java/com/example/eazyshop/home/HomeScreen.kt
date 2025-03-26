package com.example.eazyshop.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eazyshop.viewmodel.ProductViewModel

@Composable
fun HomeScreen(viewModel: ProductViewModel = hiltViewModel()) {
    val products by viewModel.products.observeAsState(initial = emptyList())

    LazyColumn {
//        items(products) { product ->
////            ProductCard(product)
//        }
    }
}
