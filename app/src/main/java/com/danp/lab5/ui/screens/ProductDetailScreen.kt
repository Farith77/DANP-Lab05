package com.danp.lab5.ui.screens

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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.danp.lab5.data.model.CartItem
import com.danp.lab5.data.model.Product
import com.danp.lab5.data.repository.ProductRepository
import com.danp.lab5.ui.navigation.AppScreens
import com.danp.lab5.ui.componets.bars.AppTopBar
import com.danp.lab5.ui.componets.buttons.*
import com.danp.lab5.ui.componets.cards.AppBottomCard

@Composable
fun ProductDetailScreen(
    productId: Int,
    navController: NavController,
    cartItems: MutableList<CartItem>,
    onAddToCart: (Product, Int) -> Unit
) {
    val product = remember(productId) {
        ProductRepository.getProductById(productId)
    }

    if (product == null) {
        navController.popBackStack()
        return
    }

    var quantity by remember { mutableIntStateOf(1) }
    var isInCart by remember {
        mutableStateOf(cartItems.any { it.product.id == product.id })
    }

    val subtotal = product.price * quantity

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
                itemCount = quantity,
                totalPrice = subtotal,
                actionLabel = if (isInCart) "Ir al carrito" else "Agregar al carrito",
                onActionClick = {
                    if (isInCart) {
                        navController.navigate(AppScreens.CART)
                    } else {
                        onAddToCart(product, quantity)
                        isInCart = true
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
            // Imagen grande del producto
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
                // Nombre y categoría
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

                // Precio
                Text(
                    text = "S/ %.2f".format(product.price),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Divider(color = MaterialTheme.colorScheme.outlineVariant)

                // Descripción
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

                Divider(color = MaterialTheme.colorScheme.outlineVariant)

                // Selector de cantidad
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
                        quantity = quantity,
                        onIncrease = { quantity++ },
                        onDecrease = { if (quantity > 1) quantity-- }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Botón agregar al carrito (secundario, complementa al AppBottomCard)
                AddToCartButton(
                    onAddToCart = {
                        if (!isInCart) {
                            onAddToCart(product, quantity)
                            isInCart = true
                        }
                    },
                    isInCart = isInCart
                )
            }
        }
    }
}