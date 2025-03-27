package com.example.eazyshop.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eazyshop.data.item.ProductCardHome
import com.example.eazyshop.viewmodel.CartViewModel
import com.example.eazyshop.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val products by viewModel.products.observeAsState(initial = emptyList())

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 8.dp, end = 8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) {
            product ->
            ProductCardHome(
                product = product,
                onAddToCart = {cartViewModel.addToCart(product)}
            )
        }
    }
}