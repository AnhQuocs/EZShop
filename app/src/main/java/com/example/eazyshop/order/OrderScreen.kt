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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.eazyshop.R
import com.example.eazyshop.data.model.Product
import com.example.eazyshop.ui.theme.EazyShopTheme
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun OrderScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    product: Product, // Product đã có quantity
    country: String,
    onCountryChange: (String) -> Unit,
    city: String,
    onCityChange: (String) -> Unit,
    district: String,
    onDistrictChange: (String) -> Unit,
    ward: String,
    onWardChange: (String) -> Unit,
    specific: String,
    onSpecificChange: (String) -> Unit,
    onOrder: () -> Unit,
    isOrderEnabled: Boolean
) {
    Scaffold(
        topBar = { TopOrderBar(navController = navController) },
        bottomBar = {
            BottomOrderBar(
                navController = navController,
                product = product, // Truyền product đã cập nhật quantity
                onOrder = onOrder,
                isOrderEnabled = isOrderEnabled
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            AddressCard(
                country = country,
                onCountryChange = onCountryChange,
                city = city,
                onCityChange = onCityChange,
                district = district,
                onDistrictChange = onDistrictChange,
                ward = ward,
                onWardChange = onWardChange,
                specific = specific,
                onSpecificChange = onSpecificChange
            )

            ProductDetailCard(product = product)

            PaymentMethodsCard()

            PaymentDetailCard(product = product)

            Text(
                buildAnnotatedString {
                    append("By clicking \"Order\", you agree to abide by the ")

                    withStyle(style = SpanStyle(color = Color(0xFF00BFFF), fontWeight = FontWeight.Bold)) {
                        append("Shopoo Terms")
                    }
                },
                lineHeight = 18.sp,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun AddressCard(
    modifier: Modifier = Modifier,
    country: String,
    onCountryChange: (String) -> Unit,
    city: String,
    onCityChange: (String) -> Unit,
    district: String,
    onDistrictChange: (String) -> Unit,
    ward: String,
    onWardChange: (String) -> Unit,
    specific: String,
    onSpecificChange: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Cải thiện padding
        ) {
            Text(
                "Address",
                color = Color(0xFFFF3333),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AddressTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    value = country,
                    onValueChange = onCountryChange,
                    label = "Country"
                )
                Spacer(Modifier.width(8.dp))
                AddressTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    value = city,
                    onValueChange = onCityChange,
                    label = "City"
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AddressTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    value = district,
                    onValueChange = onDistrictChange,
                    label = "District"
                )
                Spacer(Modifier.width(8.dp))
                AddressTextField(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(end = 6.dp),
                    value = ward,
                    onValueChange = onWardChange,
                    label = "Ward"
                )
            }

            Spacer(Modifier.height(8.dp))

            AddressTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp),
                value = specific,
                onValueChange = onSpecificChange,
                label = "Specific"
            )
        }
    }
}

@Composable
fun ProductDetailCard(
    modifier: Modifier = Modifier,
    product: Product
) {
    val wavyFont = FontFamily(Font(R.font.wavy_font))
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(230.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                        lineHeight = 16.sp,
                        fontFamily = wavyFont,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                }
                Spacer(Modifier.width(6.dp))
                Text("${product.title}", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(8.dp))

            val thrifty =
                BigDecimal(product.price * 0.05).setScale(2, RoundingMode.HALF_UP).toDouble()
            val originalPrice =
                BigDecimal(product.price + thrifty).setScale(2, RoundingMode.HALF_UP).toDouble()
            val totalPrice =
                BigDecimal((product.price - thrifty) * product.quantity).setScale(2, RoundingMode.HALF_UP)
                    .toDouble()

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                val (image, description, price, original, quantity) = createRefs()

                Image(
                    painter = rememberAsyncImagePainter(model = product.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .aspectRatio(1f)
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .constrainAs(description) {
                            start.linkTo(image.end, margin = 8.dp)
                            top.linkTo(parent.top)
                        }
                )

                Text(
                    "$${product.price}",
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
                    "x${product.quantity}",
                    fontSize = 15.sp,
                    modifier = Modifier.constrainAs(quantity) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD3D3D3))
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Shop vouchers (-5%)", fontSize = 16.sp)
                Box(
                    modifier = Modifier
                        .border(1.dp, color = Color(0xFFFF3333))
                ) {
                    Text(
                        "-$$thrifty",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD3D3D3))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (product.quantity == 1) {
                    Text(
                        "Total Price (${product.quantity} product)"
                    )
                } else {
                    Text(
                        "Total Price (${product.quantity} products)"
                    )
                }

                Text(
                    "$$totalPrice",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductDetailCardPreview() {
    EazyShopTheme {
        ProductDetailCard(
            product = Product(
                id = 1,
                title = "Laptop Gaming",
                price = 1299.99,
                image = R.drawable.phone1,
                description = "Laptop ASUS TUF Gaming A15 FA506NCR-HN047W",
                category = "Electronics",
                quantity = 1
            )
        )
    }
}

@Composable
fun PaymentMethodsCard(modifier: Modifier = Modifier) {

    var selectedMethod by remember { mutableStateOf("cash") }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                "Payment Methods",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Money,
                        contentDescription = null,
                        tint = Color(0xFFFF3333)
                    )
                    Spacer(Modifier.width(4.dp))
                    PaymentOption(
                        text = "Payment on Receipt",
                        selected = selectedMethod == "cash",
                        onSelect = { selectedMethod = "cash" }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.CreditCard,
                        contentDescription = null,
                        tint = Color(0xFFFF3333)
                    )
                    Spacer(Modifier.width(4.dp))
                    PaymentOption(
                        text = "Credit/Debit Card",
                        selected = selectedMethod == "card",
                        onSelect = { selectedMethod = "card" }
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentOption(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, modifier = Modifier.weight(1f))
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF3333))
        )
    }
}

@Composable
fun PaymentDetailCard(
    modifier: Modifier = Modifier,
    product: Product
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        val discount = BigDecimal(product.price * 0.05).setScale(2, RoundingMode.HALF_UP).toDouble()
        val totalPrice =
            BigDecimal((product.price - discount) * product.quantity).setScale(2, RoundingMode.HALF_UP)
                .toDouble()
        val shippingCharges = BigDecimal(product.price * 0.005).setScale(2, RoundingMode.HALF_UP).toDouble()

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                "Payment Details",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Price", fontSize = 14.sp)
                Text("$$totalPrice", fontSize = 14.sp)
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total shipping charges", fontSize = 14.sp)
                Text("$$shippingCharges", fontSize = 14.sp)
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Shipping fee discounts", fontSize = 14.sp)
                Text("-$$shippingCharges", fontSize = 14.sp, color = Color(0xFFFF3333))
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Payment", fontSize = 14.sp)
                Text("$$totalPrice", fontSize = 14.sp)
            }

            Spacer(Modifier.height(8.dp))


        }
    }
}

@Preview
@Composable
private fun PaymentDetailCardPreview() {
    PaymentDetailCard(
        product = Product(
            id = 1,
            title = "Laptop Gaming",
            price = 1299.99,
            image = R.drawable.laptop1,
            description = "Laptop ASUS TUF Gaming A15 FA506NCR-HN047W",
            category = "Electronics",
            quantity = 1
        )
    )
}

@Preview
@Composable
private fun PaymentMethodsCardPreview() {
    PaymentMethodsCard()
}

@Composable
fun AddressTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 14.sp) },
        leadingIcon = {
            Icon(
                Icons.Outlined.Place,
                contentDescription = null,
                tint = Color(0xFF33CC66)
            )
        },
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
        modifier = modifier.fillMaxWidth() // Cần thêm fillMaxWidth để hiển thị đầy đủ
    )
}

@Composable
fun TopOrderBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.KeyboardDoubleArrowLeft,
            contentDescription = "",
            tint = Color(0xFFFF3333),
            modifier = Modifier
                .padding(start = 8.dp)
                .size(32.dp)
                .align(Alignment.Bottom)
                .clickable { navController.popBackStack() }
        )
        Spacer(Modifier.padding(horizontal = 8.dp))
        Text(
            "Abate",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 4.dp)
        )
    }
}

@Composable
fun BottomOrderBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    product: Product,
    onOrder: () -> Unit,
    isOrderEnabled: Boolean
) {
    val discount = BigDecimal(product.price * 0.05).setScale(2, RoundingMode.HALF_UP).toDouble()
    val totalPrice =
        BigDecimal((product.price - discount) * product.quantity).setScale(2, RoundingMode.HALF_UP)
            .toDouble()
    val thrifty =
        BigDecimal(discount * product.quantity).setScale(2, RoundingMode.HALF_UP).toDouble()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text("Total ", fontSize = 14.sp, lineHeight = 18.sp)
                    Text(
                        "$$totalPrice",
                        fontSize = 18.sp,
                        color = Color(0xFFFF3333),
                        lineHeight = 18.sp
                    )
                }
                Row {
                    Text("Thrifty ", fontSize = 14.sp, lineHeight = 18.sp)
                    Text(
                        "$$thrifty",
                        fontSize = 14.sp,
                        color = Color(0xFFFF3333),
                        lineHeight = 18.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .padding(vertical = 8.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFFF3333).copy(alpha = if (isOrderEnabled) 1f else 0.5f))
                    .clickable(enabled = isOrderEnabled) { onOrder() },
                contentAlignment = Alignment.Center
            ) {
                Text("Order", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderScreen() {
    val navController = rememberNavController()

    var country by remember { mutableStateOf("Vietnam") }
    var city by remember { mutableStateOf("Hanoi") }
    var district by remember { mutableStateOf("Ba Dinh") }
    var ward by remember { mutableStateOf("Kim Ma") }
    var specific by remember { mutableStateOf("123 Doi Can") }

    OrderScreen(
        navController = navController,
        product = Product(
            id = 1,
            title = "Laptop Gaming",
            price = 1299.99,
            image = R.drawable.laptop1,
            description = "Laptop ASUS TUF Gaming A15 FA506NCR-HN047W",
            category = "Electronics",
            quantity = 1
        ),
        country = country,
        onCountryChange = { country = it },
        city = city,
        onCityChange = { city = it },
        district = district,
        onDistrictChange = { district = it },
        ward = ward,
        onWardChange = { ward = it },
        specific = specific,
        onSpecificChange = { specific = it },
        onOrder = { /* Handle order in preview */ },
        isOrderEnabled = true
    )
}

@Preview
@Composable
private fun AddressCardPreview() {
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var ward by remember { mutableStateOf("") }
    var specific by remember { mutableStateOf("") }
    EazyShopTheme {
        AddressCard(
            country = country,
            onCountryChange = { country = it },
            city = city,
            onCityChange = { city = it },
            district = district,
            onDistrictChange = { district = it },
            ward = ward,
            onWardChange = { ward = it },
            specific = specific,
            onSpecificChange = { specific = it }
        )
    }
}
