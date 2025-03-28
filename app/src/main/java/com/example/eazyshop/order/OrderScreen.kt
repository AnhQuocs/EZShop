package com.example.eazyshop.order

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
    onOrder: () -> Unit
) {
    Scaffold(
        topBar = { TopOrderBar(navController = navController) },
        bottomBar = {
            BottomOrderBar(
                navController = navController,
                product = product, // Truyền product đã cập nhật quantity
                onOrder = onOrder
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
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
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

    }
}

@Preview
@Composable
private fun ProductDetailCardPreview() {
    EazyShopTheme {
        ProductDetailCard()
    }
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
    onOrder: () -> Unit
) {
    Log.d("BottomOrderBar", "Product ID: ${product.id}, Quantity: ${product.quantity}, Price: ${product.price}")

    val discount = BigDecimal(product.price * 0.3).setScale(2, RoundingMode.HALF_UP).toDouble()
    val totalPrice = BigDecimal((product.price - discount) * product.quantity).setScale(2, RoundingMode.HALF_UP).toDouble()
    val thrifty = BigDecimal(discount * product.quantity).setScale(2, RoundingMode.HALF_UP).toDouble()

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
                    .background(Color(0xFFFF3333))
                    .clickable { onOrder() },
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
            image = R.drawable.laptop_gm,
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
        onOrder = { /* Handle order in preview */ }
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