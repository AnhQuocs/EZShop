package com.example.eazyshop.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eazyshop.R
import com.example.eazyshop.data.model.CartItem
import com.example.eazyshop.item.ProductCardHome
import com.example.eazyshop.order.CartScreen
import com.example.eazyshop.order.HistoryScreen
import com.example.eazyshop.profile.MeScreen
import com.example.eazyshop.viewmodel.CartViewModel
import com.example.eazyshop.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val products by viewModel.products.observeAsState(initial = emptyList())

    // 🔍 Lọc sản phẩm theo category
    val filteredProducts = if (selectedCategory == "All") {
        products
    } else {
        products.filter { it.category == selectedCategory }
    }

    // Snackbar host state để quản lý thông báo
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Lắng nghe sự kiện từ cartViewModel để hiển thị thông báo
    LaunchedEffect(cartViewModel.cartEvent) {
        cartViewModel.cartEvent.collect { message ->
            if (snackbarHostState.currentSnackbarData == null) { // Kiểm tra nếu chưa có Snackbar nào hiển thị
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short // Hiển thị trong thời gian ngắn
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            // Tùy chỉnh giao diện của SnackbarHost
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(8.dp)
            ) { data ->
                // Tùy chỉnh giao diện của Snackbar
                Snackbar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp), // Bo tròn góc
                    containerColor = Color(0xFF1E9584), // Màu nền xanh đậm
                    contentColor = Color.White, // Màu chữ trắng
                    action = {
                        // Tùy chỉnh nút hành động "OK"
                        TextButton(
                            onClick = { data.dismiss() }
                        ) {
                            Text(
                                text = data.visuals.actionLabel ?: "OK",
                                color = Color.Yellow // Màu vàng cho nút "OK"
                            )
                        }
                    }
                ) {
                    // Nội dung của Snackbar
                    Text(
                        text = data.visuals.message,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
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
                items(filteredProducts, key = {it.id}) { product ->
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController, cartViewModel: CartViewModel = hiltViewModel(), cartItems: List<CartItem>) {
    val pagerState = rememberPagerState(pageCount = { 4 }) // ✅ Thêm pageCount
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            EzShopBottomAppBar(
                selectedIndex = pagerState.currentPage,
                onItemSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index) // Vuốt mượt hơn
                    }
                }
            )
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState, // ✅ Đảm bảo số trang được đặt rõ ràng
            modifier = Modifier.padding(paddingValues),
            beyondBoundsPageCount = 1
        ) { page ->
            when (page) {
                0 -> HomeScreen(navController)
                1 -> CartScreen(
                    navController = navController,
                    onDeleteFromCart = { cartItem -> cartViewModel.removeFromCart(cartItem) }
                )
                2 -> HistoryScreen()
                3 -> MeScreen()
            }
        }
    }
}

@Composable
fun EzShopBottomAppBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        "Home" to Icons.Default.Home,
        "Cart" to Icons.Outlined.ShoppingCart,
        "History" to Icons.Filled.AccessTime,
        "Me" to Icons.Default.Person
    )

    NavigationBar(containerColor = Color(0xFFF5F5F5), modifier = Modifier.height(56.dp).border(0.1.dp, color = Color.LightGray)) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.padding(top = 8.dp),
                selected = selectedIndex == index, // Sử dụng selectedIndex trực tiếp
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.second,
                        contentDescription = item.first,
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedIndex == index) Color(0xFFFF6600) else Color(0xFF8A7BA2)
                    )
                },
                label = {
                    Text(
                        item.first,
                        style = TextStyle(
                            color = if (selectedIndex == index) Color(0xFFFF6600) else Color(0xFF8A7BA2),
                            fontSize = 14.sp
                        ),
                        modifier = Modifier.offset(y = (-8).dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}

@Composable
fun FilterCategory(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listCategories = listOf("All", "Laptop", "Phone", "Screen", "PC")

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
