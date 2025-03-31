package com.example.eazyshop.order

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.eazyshop.R
import com.example.eazyshop.data.model.OrderHistory
import com.example.eazyshop.data.model.Product
import com.example.eazyshop.ui.theme.EazyShopTheme
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {
    Text("History Screen")
}

@Composable
fun HistoryItemCard(
    modifier: Modifier = Modifier,
    product: Product,
    orderHistory: OrderHistory
) {

    val thrifty =
        BigDecimal(product.price * 0.05 * product.quantity).setScale(2, RoundingMode.HALF_UP).toDouble()
    val grandPrice = BigDecimal(product.price * product.quantity - thrifty).setScale(2, RoundingMode.HALF_UP)
        .toDouble()
    val originalPrice = BigDecimal(product.price * 0.05 + product.price).setScale(2, RoundingMode.HALF_UP)
        .toDouble()

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color(0xFF990000))
                    ) {
                        Text(
                            "Mail",
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(2.dp)
                        )
                    }

                    Spacer(Modifier.width(4.dp))

                    Text(text = product.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Text("Order Successfully", fontSize = 14.sp, color = Color(0xFFFF6600))
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                val (image, description, time, quantity, price, original) = createRefs()

                Image(
                    painter = rememberAsyncImagePainter(model = product.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .constrainAs(description) {
                            start.linkTo(image.end, margin = 8.dp)
                            top.linkTo(parent.top)
                        }
                )

                Text(
                    text = orderHistory.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 14.sp,
                    modifier = Modifier.constrainAs(time) {
                        start.linkTo(image.end, margin = 8.dp)
                        top.linkTo(description.bottom)
                    }
                )

                Text(
                    text = "x${product.quantity}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 14.sp,
                    modifier = Modifier.constrainAs(quantity) {
                        top.linkTo(description.bottom)
                        end.linkTo(parent.end)
                    }
                )

                Text(
                    "$${product.price}",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(price) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                )

                Text(
                    "$$originalPrice",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier.constrainAs(original) {
                        end.linkTo(price.start, margin = 2.dp)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (product.quantity == 1) {
                    Text("Total amount(1 product): ", fontSize = 14.sp)
                } else {
                    Text("Total amount(${product.quantity} products): ", fontSize = 14.sp)
                }

                Text(
                    "$${grandPrice}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(Modifier.width(100.dp))

                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Returns/Refunds",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Spacer(Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .border(1.dp, color = Color(0xFFFF6600), shape = RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "View details",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HistoryItemCardPreview() {
    EazyShopTheme {
        HistoryItemCard(
            product = Product(
                id = 1,
                title = "Laptop Gaming",
                price = 1239.99,
                image = R.drawable.laptop1,
                description = "Laptop ASUS TUF Gaming A15 FA506NCR-HN047W",
                category = "Electronics",
                quantity = 3
            ),
            orderHistory = OrderHistory(
                productId = 1,
                addressId = "",
                createdAt = LocalDateTime.now()
            )
        )
    }
}