package com.example.eazyshop.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MarkUnreadChatAlt
import androidx.compose.material.icons.filled.ProductionQuantityLimits
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ProductionQuantityLimits
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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
fun ProductDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    product: Product,
    onAddToCart: () -> Unit
) {
//    Text("\u26A1")

    var quantity by remember { mutableStateOf(1) }

    Scaffold(
        bottomBar = {
            BottomProductDetailScreen(
                navController = navController,
                onAddToCart = onAddToCart,
                product = product,
                quantity = quantity
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.weight(0.5f)
            ) {
                ConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (image, backBtn) = createRefs()
                    Image(
                        painter = painterResource(id = product.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(image) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                            }
                    )

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(35.dp)
                            .background(color = Color.LightGray)
                            .clickable { navController.popBackStack() }
                            .constrainAs(backBtn) {
                                top.linkTo(parent.top, margin = 8.dp)
                                start.linkTo(parent.start, margin = 8.dp)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.KeyboardDoubleArrowLeft,
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                }
            }

            val originalPrice = product.price + (product.price * 0.3)

            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(top = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFF3333))
                        .height(32.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "F\u26A1ASH SALE",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )

                        Text(
                            "ENDS IN 01:29:59",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFFfcf5f0))
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Text(
                                "$${product.price}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFFFF3333)
                            )
                            Spacer(Modifier.padding(horizontal = 4.dp))
                            Text(
                                "$${originalPrice}",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }

                        var isFavorite by remember { mutableStateOf(true) }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Sold 19.8K",
                                fontSize = 14.sp
                            )
                            IconButton(
                                onClick = { isFavorite = !isFavorite }
                            ) {
                                Icon(
                                    if(isFavorite)Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite,
                                    contentDescription = "like",
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    Row {
                        Box(
                            modifier = Modifier.border(1.dp, color = Color(0xFFFF3333))
                        ) {
                            Text(
                                "Buy a minimum of \$5 to get 12% off",
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 2.dp),
                                lineHeight = 18.sp,
                                color = Color(0xFFFF3333)
                            )
                        }
                        Spacer(Modifier.padding(horizontal = 4.dp))
                        Box(
                            modifier = Modifier.border(1.dp, color = Color(0xFFFF3333))
                        ) {
                            Text(
                                "Buy with Shock Deal",
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 2.dp),
                                lineHeight = 18.sp,
                                color = Color(0xFFFF3333)
                            )
                        }
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = product.description,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(12.dp),
                        lineHeight = 20.sp
                    )
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFD3D3D3)))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            Icons.Outlined.LocalShipping,
                            contentDescription = "truck",
                            tint = Color(0xFF25A998),
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.Top)
                                .padding(top = 2.dp)
                        )
                        Spacer(Modifier.padding(horizontal = 4.dp))
                        Column {
                            Text(
                                "Receive from 2 to 3 days, delivery fee 0$",
                                fontSize = 15.sp,
                                lineHeight = 18.sp
                            )
                            Text(
                                "Get a \$0.5 voucher if the order is delivered after the above time",
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 18.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFD3D3D3)))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircleOutline,
                            contentDescription = null,
                            tint = Color(0xFF25A998),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.padding(horizontal = 4.dp))
                        Text(
                            "15-Day Free Return",
                            fontSize = 15.sp
                        )
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFD3D3D3))
                    )

                    Box(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ProductionQuantityLimits, // Chọn icon phù hợp
                                contentDescription = "Quantity Icon",
                                tint = Color(0xFF25A998),
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(Modifier.padding(horizontal = 4.dp))

                            Text(text = "Quantity:", fontSize = 16.sp)

                            Spacer(Modifier.weight(1f)) // Đẩy nút qua bên phải

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(Color.White)
                                        .clickable(enabled = quantity > 1) { quantity-- }
                                        .border(1.dp, color = if (quantity > 1) Color.Gray else Color.LightGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("-", lineHeight = 1.sp, color = if (quantity > 1) Color.Black else Color.LightGray)
                                }

                                Text(
                                    text = quantity.toString(),
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )

                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(Color.White)
                                        .clickable { quantity++ }
                                        .border(1.dp, color = Color.Gray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("+", lineHeight = 0.1.sp, color = Color.Black)
                                }
                            }
                        }

                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFD3D3D3))
                    )
                }
            }
        }
    }
}

@Composable
fun BottomProductDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    product: Product,
    quantity: Int,
    onAddToCart: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Gray) // Debug xem có khoảng trắng không
    ) {
        Row(
            modifier = Modifier.fillMaxSize() // Đảm bảo Row chiếm toàn bộ Box
        ) {
            // Phần trái
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF25A998)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    imageVector = Icons.Filled.MarkUnreadChatAlt,
                    contentDescription = "Chat",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(Color.White)
                )

                Icon(
                    painter = painterResource(id = R.drawable.cart_plus_svgrepo_com),
                    contentDescription = "Cart",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onAddToCart() }
                )
            }

            // Phần phải
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFFF3333))
                    .clickable { navController.navigate("order/${product.id}?quantity=$quantity") },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Buy now",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun BottomOrderScreenPreview() {
    val navController = rememberNavController()
    EazyShopTheme {
        ProductDetailScreen(
            navController,
            product = Product(
                id = 1,
                title = "Laptop Gaming",
                price = 1299.99,
                image = R.drawable.laptop_gm,
                description = "Laptop ASUS TUF Gaming A15 FA506NCR-HN047W",
                category = "Electronics",
                quantity = 1
            ),
            onAddToCart = {},
        )
    }
}