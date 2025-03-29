package com.example.eazyshop.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.eazyshop.base.BaseProductCard
import com.example.eazyshop.data.model.Product

@Composable
fun ProductCardHome(
    navController: NavController,
    modifier: Modifier = Modifier,
    product: Product,
    onAddToCart: () -> Unit,
    onBuy: () -> Unit
) {
    BaseProductCard(
        navController = navController,
        product = product,
        buttonText = "",
        onActive = onAddToCart,
        onBuy = onBuy
    )
}