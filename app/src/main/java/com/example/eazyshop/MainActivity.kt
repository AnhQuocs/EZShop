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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eazyshop.data.model.Product
import com.example.eazyshop.home.HomeScreen
import com.example.eazyshop.order.OrderScreen
import com.example.eazyshop.ui.theme.EazyShopTheme
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
    viewModel: ProductViewModel = hiltViewModel()
) {
    val navController  = rememberNavController()
    val products by viewModel.products.observeAsState(initial = emptyList())

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }

        composable(
            "order/{productId}",
            arguments = listOf(navArgument("productId") {type = NavType.StringType})
        ) {
            backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val product = products.find { it.id == productId.toIntOrNull() } // Tìm sản phẩm trong danh sách
            if (product != null) {
                OrderScreen(navController, product = product) // Gọi OrderScreen với sản phẩm tìm được
            } else {
                Text("Product not found") // Hiển thị thông báo nếu không tìm thấy sản phẩm
            }
        }

        composable("backHome") {
            HomeScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}