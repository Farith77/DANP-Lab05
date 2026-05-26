package com.danp.lab5.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.danp.lab5.data.model.CartItem
import com.danp.lab5.data.model.Product
import com.danp.lab5.ui.screens.*

/**
 * Grafo de navegación principal de la aplicación (SRP).
 * Se han añadido las nuevas rutas solicitadas siguiendo principios SOLID.
 */
@Composable
fun NavGraph(startDestination: String = AppScreens.LOGIN) {

    val navController = rememberNavController()

    // ── Estado global del carrito (Mantenido aquí para simplicidad del laboratorio) ──
    val cartItems = remember { mutableStateListOf<CartItem>() }

    // ── Lógica del carrito ──
    fun addToCart(product: Product, quantity: Int = 1) {
        val index = cartItems.indexOfFirst { it.product.id == product.id }
        if (index != -1) {
            cartItems[index] = cartItems[index].copy(
                quantity = cartItems[index].quantity + quantity
            )
        } else {
            cartItems.add(CartItem(product = product, quantity = quantity))
        }
    }

    fun increaseQuantity(item: CartItem) {
        val index = cartItems.indexOfFirst { it.product.id == item.product.id }
        if (index != -1) {
            cartItems[index] = cartItems[index].copy(quantity = cartItems[index].quantity + 1)
        }
    }

    fun decreaseQuantity(item: CartItem) {
        val index = cartItems.indexOfFirst { it.product.id == item.product.id }
        if (index != -1) {
            if (cartItems[index].quantity > 1) {
                cartItems[index] = cartItems[index].copy(quantity = cartItems[index].quantity - 1)
            } else {
                cartItems.removeAt(index)
            }
        }
    }

    fun removeItem(item: CartItem) {
        cartItems.removeAll { it.product.id == item.product.id }
    }

    // ── NavHost ──
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login
        composable(route = AppScreens.LOGIN) {
            LoginScreen(navController = navController)
        }

        // Registro
        composable(route = AppScreens.REGISTER) {
            RegisterScreen(navController = navController)
        }

        // Home
        composable(route = AppScreens.HOME) {
            HomeScreen(
                navController = navController,
                cartItems = cartItems,
                onAddToCart = { product -> addToCart(product) }
            )
        }

        // Detalle de producto
        composable(
            route = AppScreens.DETAIL,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            ProductDetailScreen(
                productId = productId,
                navController = navController,
                cartItems = cartItems,
                onAddToCart = { product, quantity -> addToCart(product, quantity) }
            )
        }

        // Carrito
        composable(route = AppScreens.CART) {
            CartScreen(
                navController = navController,
                cartItems = cartItems,
                onQuantityIncrease = { increaseQuantity(it) },
                onQuantityDecrease = { decreaseQuantity(it) },
                onRemoveItem = { removeItem(it) }
            )
        }

        // Checkout
        composable(route = AppScreens.CHECKOUT) {
            CheckoutScreen(
                navController = navController,
                cartItems = cartItems,
                onConfirmOrder = { cartItems.clear() }
            )
        }

        // Perfil
        composable(route = AppScreens.PROFILE) {
            ProfileScreen(navController = navController)
        }
    }
}
