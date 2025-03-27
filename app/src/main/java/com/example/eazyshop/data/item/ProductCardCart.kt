package com.example.eazyshop.data.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eazyshop.data.base.BaseProductCard
import com.example.eazyshop.data.model.CartItem
import com.example.eazyshop.data.model.Product

@Composable
fun ProductCardCart(
    modifier: Modifier = Modifier,
    product: Product,
    cartItem: CartItem,
    onBuy: () -> Unit,
    onRemoveFromCart: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit
) {
    BaseProductCard(
        product = product,
        buttonText = "Xóa",
        onActive = onRemoveFromCart,
        onBuy = {},
        extraContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Button(
                    onClick = onDecreaseQuantity,
                    modifier = Modifier.size(32.dp)
                ) {
                    Text("-")
                }

                Text(
                    text = cartItem.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 16.sp
                )

                Button(
                    onClick = onIncreaseQuantity,
                    modifier = Modifier.size(32.dp)
                ) {
                    Text("+")
                }
            }
        }
    )
}