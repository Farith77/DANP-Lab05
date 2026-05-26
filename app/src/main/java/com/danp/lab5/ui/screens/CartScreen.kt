package com.danp.lab5.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danp.lab5.data.model.CartItem
import com.danp.lab5.ui.navigation.AppScreens
import com.danp.lab5.ui.componets.cards.AppBottomCard
import com.danp.lab5.ui.componets.cards.CartItemCard
import com.danp.lab5.ui.componets.bars.AppTopBar

/**
 * Pantalla del carrito de compras (SRP).
 * Se encarga de mostrar la lista de ítems agregados y el total.
 */
@Composable
fun CartScreen(
    navController: NavController,
    cartItems: MutableList<CartItem>,
    onQuantityIncrease: (CartItem) -> Unit,
    onQuantityDecrease: (CartItem) -> Unit,
    onRemoveItem: (CartItem) -> Unit
) {
    val total = cartItems.sumOf { it.product.price * it.quantity }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Mi carrito",
                showBackButton = true,
                cartItemCount = cartItems.size,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            AppBottomCard(
                itemCount = cartItems.size,
                totalPrice = total,
                actionLabel = "Finalizar compra",
                enabled = cartItems.isNotEmpty(),
                onActionClick = { navController.navigate(AppScreens.CHECKOUT) }
            )
        }
    ) { paddingValues ->

        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.outlineVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Tu carrito está vacío",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Agrega productos desde la tienda",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Spacer(modifier = Modifier.height(4.dp)) }

                items(
                    items = cartItems,
                    key = { it.product.id }
                ) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onQuantityIncrease = { onQuantityIncrease(it) },
                        onQuantityDecrease = { onQuantityDecrease(it) },
                        onRemoveItem = { onRemoveItem(it) }
                    )
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
        }
    }
}
