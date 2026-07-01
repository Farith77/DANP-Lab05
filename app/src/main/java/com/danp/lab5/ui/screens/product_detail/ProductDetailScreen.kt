package com.danp.lab5.ui.screens.product_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.danp.lab5.data.model.CartItem
import com.danp.lab5.data.model.Product
import com.danp.lab5.ui.navigation.AppScreens
import com.danp.lab5.ui.components.bars.AppTopBar
import com.danp.lab5.ui.components.buttons.*
import com.danp.lab5.ui.components.cards.AppBottomCard
import androidx.compose.material3.DividerDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun ProductDetailScreen(
    productId: Int,
    navController: NavController,
    viewModel: ProductDetailViewModel,
    cartItems: List<CartItem>,
    onAddToCart: (Product, Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isInCart = cartItems.any { it.product.id == productId }

    LaunchedEffect(productId, isInCart) {
        viewModel.loadProduct(productId, isInCart)
    }

    if (uiState.isLoading || uiState.product == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val product = uiState.product ?: return

    val subtotal = product.price * uiState.quantity

    Scaffold(
        topBar = {
            AppTopBar(
                title = product.name,
                showBackButton = true,
                cartItemCount = cartItems.size,
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate(AppScreens.CART) }
            )
        },
        bottomBar = {
            AppBottomCard(
                itemCount = uiState.quantity,
                totalPrice = subtotal,
                actionLabel = if (uiState.isInCart) "Ir al carrito" else "Agregar al carrito",
                onActionClick = {
                    if (uiState.isInCart) {
                        navController.navigate(AppScreens.CART)
                    } else {
                        onAddToCart(product, uiState.quantity)
                        viewModel.setInCart(true)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = product.category,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "S/ %.2f".format(product.price),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )

                HorizontalDivider(
                    thickness = DividerDefaults.Thickness,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                HorizontalDivider(
                    thickness = DividerDefaults.Thickness,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cantidad",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    QuantitySelector(
                        quantity = uiState.quantity,
                        onIncrease = { viewModel.onQuantityChange(uiState.quantity + 1) },
                        onDecrease = { viewModel.onQuantityChange(uiState.quantity - 1) }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                AddToCartButton(
                    onAddToCart = {
                        if (!uiState.isInCart) {
                            onAddToCart(product, uiState.quantity)
                            viewModel.setInCart(true)
                        }
                    },
                    isInCart = uiState.isInCart
                )
            }
        }
    }
}
