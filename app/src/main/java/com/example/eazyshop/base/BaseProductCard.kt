package com.example.eazyshop.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eazyshop.R
import com.example.eazyshop.data.model.Product
import com.example.eazyshop.ui.theme.EazyShopTheme

@Composable
fun BaseProductCard(
    navController: NavController,
    modifier: Modifier = Modifier,
    product: Product,
    buttonText: String,
    onActive: () -> Unit,
    onBuy: () -> Unit,
    extraContent: @Composable () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(200.dp)
            .padding(6.dp)
            .height(330.dp)
            .clickable { navController.navigate("productDetail/${product.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(350.dp)
        ) {
            val (title, price, image, description, active) = createRefs()

            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Image(
                painter = painterResource(id = product.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .constrainAs(image) {
                        top.linkTo(title.bottom, margin = 12.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = product.description,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .constrainAs(description) {
                    top.linkTo(image.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Text(
                text = "$${product.price}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(price) {
//                    top.linkTo(description.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(active.top)
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .constrainAs(active) {
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Button(
                    onClick = {onBuy()},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(45.dp)
                        .padding(top = 8.dp)
                        .weight(0.7f)
                ) {
                    Text(
                        "Buy",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier.weight(0.3f)
                ) {
                    IconButton(
                        onClick = {onActive()},
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.cart_plus_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BaseProductCardPreview() {
    val navController = rememberNavController()
    EazyShopTheme {
        BaseProductCard(
            navController = navController,
            product = Product(
                id = 1,
                title = "Laptop Gaming",
                price = 1299.99,
                image = R.drawable.laptop_gm,
                description = "Laptop gaming cấu hình cao",
                category = "Electronics",
                quantity = 1
            ),
            buttonText = "Mua ngay",
            onActive = { /* TODO: Handle button click */ },
            onBuy = {},
            extraContent = {
                Text(
                    text = "🔥 Giảm giá 10%",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        )
    }
}