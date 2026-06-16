package com.danp.lab5.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.danp.lab5.ui.screens.cart.CartScreen
import com.danp.lab5.ui.screens.cart.CartViewModel
import com.danp.lab5.ui.screens.checkout.CheckoutScreen
import com.danp.lab5.ui.screens.checkout.CheckoutViewModel
import com.danp.lab5.ui.screens.home.HomeScreen
import com.danp.lab5.ui.screens.home.HomeViewModel
import com.danp.lab5.ui.screens.login.LoginScreen
import com.danp.lab5.ui.screens.login.LoginViewModel
import com.danp.lab5.ui.screens.product_detail.ProductDetailScreen
import com.danp.lab5.ui.screens.product_detail.ProductDetailViewModel
import com.danp.lab5.ui.screens.profile.ProfileScreen
import com.danp.lab5.ui.screens.profile.ProfileViewModel
import com.danp.lab5.ui.screens.register.RegisterScreen
import com.danp.lab5.ui.screens.register.RegisterViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(startDestination: String = AppScreens.LOGIN) {

    val navController = rememberNavController()

    // Shared CartViewModel
    val cartViewModel: CartViewModel = koinViewModel()
    val cartItems = cartViewModel.cartItems

    // ── NavHost ──
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login
        composable(route = AppScreens.LOGIN) {
            val loginViewModel: LoginViewModel = koinViewModel()
            LoginScreen(
                navController = navController,
                viewModel = loginViewModel
            )
        }

        // Registro
        composable(route = AppScreens.REGISTER) {
            val registerViewModel: RegisterViewModel = koinViewModel()
            RegisterScreen(
                navController = navController,
                viewModel = registerViewModel
            )
        }

        // Home
        composable(route = AppScreens.HOME) {
            val homeViewModel: HomeViewModel = koinViewModel()
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel,
                cartItems = cartItems,
                onAddToCart = { product -> cartViewModel.addToCart(product) }
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
            val detailViewModel: ProductDetailViewModel = koinViewModel()
            ProductDetailScreen(
                productId = productId,
                navController = navController,
                viewModel = detailViewModel,
                cartItems = cartItems,
                onAddToCart = { product, quantity -> cartViewModel.addToCart(product, quantity) }
            )
        }

        // Carrito
        composable(route = AppScreens.CART) {
            CartScreen(
                navController = navController,
                viewModel = cartViewModel
            )
        }

        // Checkout
        composable(route = AppScreens.CHECKOUT) {
            val checkoutViewModel: CheckoutViewModel = koinViewModel()
            CheckoutScreen(
                navController = navController,
                viewModel = checkoutViewModel,
                cartItems = cartItems,
                onConfirmOrder = { cartViewModel.clearCart() }
            )
        }

        // Perfil
        composable(route = AppScreens.PROFILE) {
            val profileViewModel: ProfileViewModel = koinViewModel()
            ProfileScreen(
                navController = navController,
                viewModel = profileViewModel
            )
        }
    }
}
