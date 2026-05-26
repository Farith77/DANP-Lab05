package com.danp.lab5.ui.navigation

/**
 * Objeto que centraliza todas las rutas de navegación de la app.
 * Evita el uso de strings hardcodeados en las screens (SRP).
 */
object AppScreens {
    const val LOGIN      = "login"
    const val REGISTER   = "register"
    const val HOME       = "home"
    const val DETAIL     = "detail/{productId}"
    const val CART       = "cart"
    const val CHECKOUT   = "checkout"
    const val PROFILE    = "profile"

    /** Genera la ruta de detalle con el id real del producto. */
    fun detail(productId: Int) = "detail/$productId"
}
