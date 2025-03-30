package com.example.eazyshop.order

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.eazyshop.R
import com.example.eazyshop.data.model.Address
import com.example.eazyshop.data.model.OrderHistory
import com.example.eazyshop.data.model.Product
import com.example.eazyshop.ui.theme.EazyShopTheme
import com.example.eazyshop.viewmodel.AddressViewModel
import com.example.eazyshop.viewmodel.OrderHistoryViewModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun OrderDetailScreen(
    modifier: Modifier = Modifier,
    addressViewModel: AddressViewModel = hiltViewModel(),
    orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel(),
    productId: Int,
    product: Product
) {
    val addresses by addressViewModel.getAddress(productId).collectAsState(initial = emptyList())
    val orderHistories by orderHistoryViewModel.getOrdersByProductId(productId)
        .collectAsState(initial = emptyList())

    val orderHistory = orderHistories.firstOrNull { it.productId == productId }
    val address = addresses.find { it.orderId == orderHistory?.addressId }

    Scaffold(
        topBar = {
            TopOrderScreenBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (orderHistory != null && address != null) {
                OrderInfoCard(
                    address = address,
                    orderHistory = orderHistory
                )
            }

            ProductInfoCard(product = product)
        }
    }
}

@Composable
fun TopOrderScreenBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    Icons.Default.KeyboardDoubleArrowLeft,
                    contentDescription = null,
                    tint = Color(0xFFFF3333),
                    modifier = Modifier.size(32.dp)
                )
            }

            Text(
                "Order Information",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun OrderInfoCard(
    modifier: Modifier = Modifier,
    address: Address,
    orderHistory: OrderHistory
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(240.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E9584))
            ) {
                Text(
                    "Placed order",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (shippingCarrier, carrierName, condition) = createRefs()

                    Text(
                        "Shipping carrier",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 18.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .constrainAs(shippingCarrier) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            }
                    )

                    Text(
                        "Shopoo Express",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        modifier = Modifier.constrainAs(carrierName) {
                            top.linkTo(shippingCarrier.bottom)
                            start.linkTo(parent.start)
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(condition) {
                                top.linkTo(carrierName.bottom, margin = 12.dp)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircleOutline,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(vertical = 1.dp)
                                .size(16.dp)
                                .align(Alignment.Top)
                        )

                        Column(
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Text(
                                "Order placed successfully",
                                lineHeight = 18.sp,
                                fontSize = 14.sp,
                                color = Color(0xFF1E9584)
                            )

                            Text(
                                text = orderHistory.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                                lineHeight = 18.sp,
                                fontSize = 12.sp,
                                color = Color.Gray
                            ) //Demo
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD3D3D3))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    "Pickup address",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Place,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .size(18.dp)
                            .align(Alignment.Top)
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Row {
                            Text("User", fontSize = 14.sp)
                            Text(" (+XX) XXXX XXX XXXX", fontSize = 11.sp, color = Color.Gray)
                        }

                        Text(
                            text = address.specific + ", " +
                                    address.ward + " Ward, " +
                                    address.district + " District, " +
                                    address.city + ", " +
                                    address.country,
                            fontSize = 12.sp,
                            lineHeight = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductInfoCard(
    modifier: Modifier = Modifier,
    product: Product
) {
    val wavyFont = FontFamily(Font(R.font.wavy_font))

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {


            val thrifty =
                BigDecimal(product.price * 0.05).setScale(2, RoundingMode.HALF_UP).toDouble()
            val originalPrice =
                BigDecimal(product.price + thrifty).setScale(2, RoundingMode.HALF_UP).toDouble()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF990000))
                ) {
                    Text(
                        "Mail",
                        fontSize = 15.sp,
                        lineHeight = 16.sp,
                        fontFamily = wavyFont,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                }
                Spacer(Modifier.width(6.dp))
                Text(text = product.title, fontWeight = FontWeight.Bold)
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val (image, description, quantity, price, original) = createRefs()

                Image(
                    painter = rememberAsyncImagePainter(model = product.image),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .fillMaxWidth(0.25f)
                        .aspectRatio(1f)
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
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .constrainAs(description) {
                            start.linkTo(image.end, margin = 8.dp)
                            top.linkTo(parent.top)
                        }
                )

                Text(
                    "x${product.quantity}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.constrainAs(quantity) {
                        start.linkTo(image.end, margin = 8.dp)
                        top.linkTo(description.bottom)
                    }
                )

                Text(
                    "$${product.price}",
                    fontSize = 14.sp,
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
                        bottom.linkTo(parent.bottom)
                        end.linkTo(price.start, margin = 4.dp)
                    }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD3D3D3))
            )

            ExpandablePriceSection(product = product)
        }
    }
}

@Composable
fun ExpandablePriceSection(
    modifier: Modifier = Modifier,
    product: Product
) {
    var expanded by remember { mutableStateOf(false) }

    val thrifty =
        BigDecimal(product.price * 0.05 * product.quantity).setScale(2, RoundingMode.HALF_UP).toDouble()
    val totalPrice =
        BigDecimal(product.price * product.quantity).setScale(2, RoundingMode.HALF_UP)
            .toDouble()
    val shippingCharges = BigDecimal(product.price * 0.005).setScale(2, RoundingMode.HALF_UP).toDouble()
    val grandPrice = BigDecimal(product.price * product.quantity - thrifty).setScale(2, RoundingMode.HALF_UP)
        .toDouble()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Row{
                Text(
                    text = "Grand total: ",
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                )
                Text(
                    "$$grandPrice",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
            }

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

        // Hiển thị phần mở rộng với hiệu ứng
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
//                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFD3D3D3))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Text("Total price", fontSize = 13.sp)
                    Text("$$totalPrice", fontSize = 13.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Text("Shipping charges", fontSize = 13.sp)
                    Text("$$shippingCharges", fontSize = 13.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Text("Preferential shipping rates", fontSize = 13.sp)
                    Text("-$$shippingCharges", fontSize = 13.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                Text("Shopoo Voucher (-5% total price)", fontSize = 13.sp)
                    Text("-$$thrifty", fontSize = 13.sp)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductInfoCardPreview() {
    EazyShopTheme {
        ProductInfoCard(
            product = Product(
                id = 1,
                title = "Laptop Gaming",
                price = 1239.99,
                image = R.drawable.laptop1,
                description = "Laptop ASUS TUF Gaming A15 FA506NCR-HN047W",
                category = "Electronics",
                quantity = 3
            )
        )
    }
}

@Preview
@Composable
private fun OrderInfoCardPreview() {
    EazyShopTheme {
        OrderInfoCard(
            address = Address(
                productId = 1,
                country = "Vietnam",
                city = "Hanoi",
                district = "Ha Dong",
                ward = "Yen Nghia",
                specific = "Phenikaa University"
            ),
            orderHistory = OrderHistory(
                productId = 1,
                addressId = "",
                createdAt = LocalDateTime.now()
            )
        )
    }
}

