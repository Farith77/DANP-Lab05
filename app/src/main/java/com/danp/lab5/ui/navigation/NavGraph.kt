package com.danp.lab5.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danp.lab5.EcommerceApp
import com.danp.lab5.data.model.CartItem
import com.danp.lab5.data.model.Product
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
import com.danp.lab5.ui.viewmodel.ViewModelFactory

@Composable
fun NavGraph(startDestination: String = AppScreens.LOGIN) {

    val context = LocalContext.current
    val app = context.applicationContext as EcommerceApp
    val factory = remember { ViewModelFactory(app.productRepository, app.userRepository) }

    val navController = rememberNavController()

    // Shared CartViewModel
    val cartViewModel: CartViewModel = viewModel(factory = factory)
    val cartItems = cartViewModel.cartItems

    // ── NavHost ──
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login
        composable(route = AppScreens.LOGIN) {
            val loginViewModel: LoginViewModel = viewModel(factory = factory)
            LoginScreen(
                navController = navController,
                viewModel = loginViewModel
            )
        }

        // Registro
        composable(route = AppScreens.REGISTER) {
            val registerViewModel: RegisterViewModel = viewModel(factory = factory)
            RegisterScreen(
                navController = navController,
                viewModel = registerViewModel
            )
        }

        // Home
        composable(route = AppScreens.HOME) {
            val homeViewModel: HomeViewModel = viewModel(factory = factory)
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
            val detailViewModel: ProductDetailViewModel = viewModel(factory = factory)
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
            val checkoutViewModel: CheckoutViewModel = viewModel(factory = factory)
            CheckoutScreen(
                navController = navController,
                viewModel = checkoutViewModel,
                cartItems = cartItems,
                onConfirmOrder = { cartViewModel.clearCart() }
            )
        }

        // Perfil
        composable(route = AppScreens.PROFILE) {
            val profileViewModel: ProfileViewModel = viewModel(factory = factory)
            ProfileScreen(
                navController = navController,
                viewModel = profileViewModel
            )
        }
    }
}
