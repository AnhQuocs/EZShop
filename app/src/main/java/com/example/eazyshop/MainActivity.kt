package com.example.eazyshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eazyshop.check.CheckOrderScreen
import com.example.eazyshop.data.model.Address
import com.example.eazyshop.data.model.OrderHistory
import com.example.eazyshop.home.HomeScreen
import com.example.eazyshop.home.MainScreen
import com.example.eazyshop.order.OrderScreen
import com.example.eazyshop.order.ProductDetailScreen
import com.example.eazyshop.ui.theme.EazyShopTheme
import com.example.eazyshop.viewmodel.AddressViewModel
import com.example.eazyshop.viewmodel.CartViewModel
import com.example.eazyshop.viewmodel.OrderHistoryViewModel
import com.example.eazyshop.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EazyShopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MaterialTheme(colorScheme = lightColorScheme()) {
                        Greeting(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel(),
    orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel()
) {
    val navController  = rememberNavController()
    val products by viewModel.products.observeAsState(initial = emptyList())
    val cartItems by cartViewModel.cartItems.observeAsState(initial = emptyList())

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            MainScreen(navController, cartViewModel = cartViewModel, cartItems = cartItems)
        }

        composable(
            "productDetail/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.StringType})
        ) {
            backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val product = products.find { it.id == productId.toIntOrNull() } // Tìm sản phẩm trong danh sách
            if (product != null) {
                ProductDetailScreen (
                    navController,
                    product = product,
                    onAddToCart = {cartViewModel.addToCart(product)},
                ) // Gọi OrderScreen với sản phẩm tìm được
            } else {
                Text("Product not found") // Hiển thị thông báo nếu không tìm thấy sản phẩm
            }
        }

        composable("backHome",
            enterTransition = {null},
            exitTransition = {null}
        ) {
            HomeScreen(navController)
        }

        composable(
            "order/{productId}?quantity={quantity}",
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType },
                navArgument("quantity") { type = NavType.IntType; defaultValue = 1 }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val quantity = backStackEntry.arguments?.getInt("quantity") ?: 1

            val product = products.find { it.id == productId.toIntOrNull() }

            if (product != null) {
                var country by remember { mutableStateOf("") }
                var city by remember { mutableStateOf("") }
                var district by remember { mutableStateOf("") }
                var ward by remember { mutableStateOf("") }
                var specific by remember { mutableStateOf("") }

                val isValid by remember {
                    derivedStateOf {
                        country.isNotBlank() && city.isNotBlank() && district.isNotBlank() &&
                                ward.isNotBlank() && specific.isNotBlank()
                    }
                }

                OrderScreen(
                    navController = navController,
                    product = product.copy(quantity = quantity), // Truyền sản phẩm có số lượng đúng
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
                    onOrder = {
                        if (isValid) {
                            val address = Address(
                                productId = product.id,
                                country = country,
                                city = city,
                                district = district,
                                ward = ward,
                                specific = specific
                            )
                            addressViewModel.saveAddress(address)

                            val orderHistory = OrderHistory(
                                productId = product.id,
                                addressId = address.orderId
                            )
                            orderHistoryViewModel.insertOrder(orderHistory)

                            navController.navigate("check/${product.id}")
                        }
                    },
                    isOrderEnabled = isValid  // Truyền trạng thái vào để vô hiệu hóa nút Order
                )
            } else {
                Text("Product not found") // Hiển thị nếu không có sản phẩm
            }
        }

        composable("check/{productId}")
        { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val product = products.find { it.id == productId.toIntOrNull() } // Tìm sản phẩm trong danh sách
            if (product != null) {
                CheckOrderScreen (
                    product = product,
                    navController = navController,
                ) // Gọi OrderScreen với sản phẩm tìm được
            } else {
                Text("Product not found") // Hiển thị thông báo nếu không tìm thấy sản phẩm
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}