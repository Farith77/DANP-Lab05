package com.danp.lab5.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danp.lab5.data.model.CartItem
import com.danp.lab5.data.model.Product
import com.danp.lab5.ui.components.bars.SearchBar
import com.danp.lab5.ui.navigation.AppScreens
import com.danp.lab5.ui.components.bars.AppTopBar
import com.danp.lab5.ui.components.cards.*

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    cartItems: List<CartItem>,
    onAddToCart: (Product) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // ✅ Ahora se recalcula cada vez que uiState cambia
    val filteredProducts = if (uiState.searchQuery.isBlank()) {
        uiState.products
    } else {
        uiState.products.filter {
            it.name.contains(uiState.searchQuery, ignoreCase = true) ||
                    it.category.contains(uiState.searchQuery, ignoreCase = true)
        }
    }

    val total = cartItems.sumOf { it.product.price * it.quantity }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Tienda",
                showBackButton = false,
                cartItemCount = cartItems.size,
                onCartClick = { navController.navigate(AppScreens.CART) },
                onProfileClick = { navController.navigate(AppScreens.PROFILE) }
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                AppBottomCard(
                    itemCount = cartItems.size,
                    totalPrice = total,
                    actionLabel = "Ir al carrito",
                    onActionClick = { navController.navigate(AppScreens.CART) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it) },
                onClearQuery = { viewModel.onSearchQueryChange("") }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = {
                            navController.navigate(AppScreens.detail(product.id))
                        }
                    )
                }
            }
        }
    }
}
