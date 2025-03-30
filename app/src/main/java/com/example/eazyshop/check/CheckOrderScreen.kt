package com.example.eazyshop.check

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eazyshop.R
import com.example.eazyshop.data.model.Product

@Composable
fun CheckOrderScreen(
    navController: NavController,
    product: Product,
    modifier: Modifier = Modifier
) {
    Log.d("OrderDetailScreen", "Received quantity: ${product.quantity}")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        ConstraintLayout(
            modifier = Modifier.weight(0.3f)
        ) {
            val (orderSuccess, backProductDetailBtn, onCart) = createRefs()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFF3333))
                    .constrainAs(orderSuccess) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            ) {
                Text(
                    "Order Successfully",
                    color = Color.White,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp, bottom = 16.dp)
                )

                Text(
                    "Pay attention to the phone call from the shipper to receive the goods!",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, color = Color.White, shape = RoundedCornerShape(4.dp))
                            .clickable { navController.navigate("home") },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Home", color = Color.White, lineHeight = 36.sp)
                    }

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, color = Color.White, shape = RoundedCornerShape(4.dp))
                            .clickable { navController.navigate("orderInfo/${product.id}?quantity=${product.quantity}") },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Purchase Order", color = Color.White, lineHeight = 36.sp)
                    }
                }
            }

            IconButton(
                onClick = {
                    navController.navigate("productDetail/${product.id}")
                },
                modifier = Modifier.constrainAs(backProductDetailBtn) {
                    top.linkTo(parent.top, margin = 24.dp)
                    start.linkTo(parent.start)
                }
            ) {
                Icon(
                    Icons.Default.KeyboardDoubleArrowLeft,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFD3D3D3))
        )

        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.CheckCircleOutline,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                tint = Color(0xFF33CC66)
            )
        }


    }
}

@Preview
@Composable
private fun CheckOrderScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme(colorScheme = lightColorScheme()) {
        CheckOrderScreen(
            product = Product(
                id = 1,
                title = "Laptop Gaming",
                price = 1299.99,
                image = R.drawable.laptop1,
                description = "Laptop ASUS TUF Gaming A15 FA506NCR-HN047W",
                category = "Electronics",
                quantity = 1
            ),
            navController = navController
        )
    }
}