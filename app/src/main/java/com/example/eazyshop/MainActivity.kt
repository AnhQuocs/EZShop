package com.example.eazyshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.example.eazyshop.order.OrderDetailScreen
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
    val navController = rememberNavController()
    val products by viewModel.products.observeAsState(initial = emptyList())

    NavHost(
        navController = navController,
        startDestination = "home",
        // Thêm hiệu ứng chuyển cảnh mặc định cho tất cả các màn hình
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth }, // Slide từ phải sang trái
                animationSpec = tween(300) // Thời gian 300ms
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth }, // Slide ra bên trái
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth }, // Slide từ trái sang phải khi quay lại
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth }, // Slide ra bên phải khi quay lại
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        composable("home") {

            MainScreen(
                navController,
                cartViewModel = cartViewModel
            )
        }

        composable(
            "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val product = products.find { it.id == productId.toIntOrNull() }
            if (product != null) {
                ProductDetailScreen(
                    navController,
                    product = product,
                    onAddToCart = { cartViewModel.addToCart(product) },
                )
            } else {
                Text("Product not found")
            }
        }

        composable(
            "backHome",
            enterTransition = { null }, // Tắt hiệu ứng cho màn hình này nếu cần
            exitTransition = { null }
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
                    product = product.copy(quantity = quantity),
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
                                title = product.title,
                                description = product.description,
                                price = product.price.toFloat(),
                                quantity = quantity,
                                addressId = address.orderId // Liên kết với addressId
                            )
                            orderHistoryViewModel.insertOrder(orderHistory)

                            navController.navigate("check/${product.id}?quantity=$quantity")
                        }
                    },
                    isOrderEnabled = isValid
                )
            } else {
                Text("Product not found")
            }
        }

        composable(
            "check/{productId}?quantity={quantity}",
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType },
                navArgument("quantity") { type = NavType.IntType; defaultValue = 1 }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val quantity = backStackEntry.arguments?.getInt("quantity") ?: 1

            val product = products.find { it.id == productId.toIntOrNull() }
            if (product != null) {
                CheckOrderScreen(
                    product = product.copy(quantity = quantity),
                    navController = navController
                )
            } else {
                Text("Product not found")
            }
        }

        composable(
            "orderInfo/{productId}?quantity={quantity}",
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType },
                navArgument("quantity") { type = NavType.IntType; defaultValue = 1 }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            val product = products.find { it.id == productId }
            val quantity = backStackEntry.arguments?.getInt("quantity") ?: product?.quantity ?: 1

            if (productId != null && product != null) {
                OrderDetailScreen(
                    navController = navController,
                    productId = productId,
                    product = product.copy(quantity = quantity),
                    addressViewModel = addressViewModel,
                    orderHistoryViewModel = orderHistoryViewModel
                )
            } else {
                Text("Invalid Product ID or Product not found")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}