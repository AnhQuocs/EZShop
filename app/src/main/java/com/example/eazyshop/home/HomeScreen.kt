package com.example.eazyshop.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eazyshop.R
import com.example.eazyshop.item.ProductCardHome
import com.example.eazyshop.ui.theme.EazyShopTheme
import com.example.eazyshop.viewmodel.CartViewModel
import com.example.eazyshop.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    var isShowTabContent by remember { mutableStateOf(true) }

    var selectedCategory by remember { mutableStateOf("All") }
    val products by viewModel.products.observeAsState(initial = emptyList())

    // 🔍 Lọc sản phẩm theo category
    val filteredProducts = if (selectedCategory == "All") {
        products
    } else {
        products.filter { it.category == selectedCategory }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = isShowTabContent) {
                EzShopBottomAppBar(
                    onOpenHome = {},
                    onCart = {},
                    onHistory = {}
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            val wavyFont = FontFamily(Font(R.font.wavy_font))

            Text("Shopoo",
                fontFamily = wavyFont,
                fontSize = 58.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFFF6600)
            )


            FilterCategory(
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = category

                }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, start = 8.dp, end = 8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCardHome(
                        navController = navController,
                        product = product,
                        onAddToCart = { cartViewModel.addToCart(product) },
                        onBuy = {navController.navigate("productDetail/${product.id}")}
                    )
                }
            }
        }
    }
}

@Composable
fun EzShopBottomAppBar(
    modifier: Modifier = Modifier,
    onOpenHome: () -> Unit,
    onCart: () -> Unit,
    onHistory: () -> Unit
) {
    val iconSelected = NavigationBarItemDefaults.colors(
        indicatorColor = Color.Transparent
    )

    NavigationBar(
        containerColor = Color.Black,
        modifier = Modifier.height(56.dp)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { onOpenHome() },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "home",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            },
            label = { Text("Home", style = TextStyle(color = Color.White, fontSize = 14.sp))},
            colors = iconSelected
        )

         NavigationBarItem(
            selected = false,
            onClick = { onOpenHome() },
            icon = {
                Icon(
                    Icons.Outlined.ShoppingCart,
                    contentDescription = "cart",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Cart", style = TextStyle(color = Color.White, fontSize = 14.sp))},
            colors = iconSelected
        )

         NavigationBarItem(
            selected = false,
            onClick = { onOpenHome() },
            icon = {
                Icon(
                    Icons.Filled.AccessTime,
                    contentDescription = "history",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("History", style = TextStyle(color = Color.White, fontSize = 14.sp))},
            colors = iconSelected
        )

        NavigationBarItem(
            selected = false,
            onClick = { onOpenHome() },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "me",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Me", style = TextStyle(color = Color.White, fontSize = 14.sp))},
            colors = iconSelected
        )
    }
}

@Composable
fun FilterCategory(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listCategories = listOf("All", "Food", "Clothes", "Electronics", "Book")

    LazyRow(modifier = Modifier) {
        items(listCategories) { category ->
            val isSelected = selectedCategory == category

            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = { Text(category, fontSize = 16.sp) },
                modifier = Modifier.padding(horizontal = 8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color.Black,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color.Black
                )
            )
        }
    }
}

@Preview
@Composable
private fun BottomAppBarPreview() {
    EazyShopTheme {
        EzShopBottomAppBar(
            onOpenHome = {},
            onCart = {},
            onHistory = {}
        )
    }
}