package com.example.eazyshop.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.eazyshop.R
import com.example.eazyshop.data.model.CartItem
import com.example.eazyshop.viewmodel.CartViewModel
import com.example.eazyshop.viewmodel.ProductViewModel
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun CartScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onDeleteFromCart: (CartItem) -> Unit,
    cartViewModel: CartViewModel = hiltViewModel(),
    viewModel: ProductViewModel = hiltViewModel()
) {
    val cartItems by cartViewModel.cartItems.observeAsState(initial = emptyList())

    val wavyFont = FontFamily(Font(R.font.wavy_font))

    val products by viewModel.products.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
    ) {
        Text(
            "Cart",
            fontSize = 32.sp,
            fontFamily = wavyFont,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFFFF6600),
            modifier = Modifier.fillMaxWidth()
        )

        if (cartItems.isEmpty()) {
            Text(
                "No orders yet!",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 1.dp), // ✅ Giúp vuốt mượt hơn
                verticalArrangement = Arrangement.spacedBy(8.dp) // ✅ Giãn cách giúp mượt hơn
            ) {
                items(cartItems, key = {it.productId}) {
                    item ->
                    val product = products.find { it.id == item.productId }
                    CartItemCard(cartItem = item, onDeleteFromCart = { onDeleteFromCart(item) }, onBuy = { product?.let { navController.navigate("productDetail/${it.id}") } })
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    onDeleteFromCart: () -> Unit,
    onBuy: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(4.dp)
            .height(125.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        val wavyFont = FontFamily(Font(R.font.wavy_font))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFCC0000))
            ) {
                Text(
                    "Mail",
                    lineHeight = 16.sp,
                    fontSize = 15.sp,
                    fontFamily = wavyFont,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            }
            Spacer(Modifier.width(6.dp))
            Text(text = cartItem.title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            val (image, description, price, original, quantity, deleteBtn, buyBtn) = createRefs()

            val originalPrice =
                BigDecimal(cartItem.price + cartItem.price * 0.05f).setScale(2, RoundingMode.HALF_UP)
                    .toDouble()

            Image(
                painter = rememberAsyncImagePainter(model = cartItem.image),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1f)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(
                text = cartItem.description,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .constrainAs(description) {
                        start.linkTo(image.end, margin = 8.dp)
                        top.linkTo(parent.top)
                    }
            )

            Text(
                "$${cartItem.price}",
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(price) {
                    start.linkTo(image.end, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                }
            )

            Text(
                "$$originalPrice",
                fontSize = 12.sp,
                color = Color.Gray,
                textDecoration = TextDecoration.LineThrough,
                modifier = Modifier.constrainAs(original) {
                    start.linkTo(price.end, margin = 4.dp)
                    bottom.linkTo(parent.bottom)
                }
            )

            Text(
                "x${cartItem.quantity}",
                fontSize = 15.sp,
                modifier = Modifier.constrainAs(quantity) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(buyBtn.start, margin = 4.dp)
                }
            )

            Button(
                onClick = {onDeleteFromCart()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF3333)
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(height = 30.dp, width = 62.dp)
                    .constrainAs(deleteBtn) {
                        start.linkTo(description.end, margin = 4.dp)
                        end.linkTo(parent.end)
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.DeleteForever,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "Delete",
                        fontSize = 13.sp
                    )
                }
            }

            Button(
                onClick = {onBuy()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107)
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(height = 30.dp, width = 62.dp)
                    .constrainAs(buyBtn) {
                        top.linkTo(deleteBtn.bottom, margin = 4.dp)
                        start.linkTo(description.end, margin = 4.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Row{
                    Text(
                        "Buy"
                    )
                }
            }
        }
    }
}