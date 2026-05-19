package com.danp.lab5.data

object SampleData {

    val products = listOf(

        Product(
            id = 1,
            name = "Laptop Gamer",
            price = 3500.00,
            description = "Laptop de alto rendimiento para juegos y programación.",
            category = "Tecnología"
        ),

        Product(
            id = 2,
            name = "Mouse RGB",
            price = 85.50,
            description = "Mouse ergonómico con iluminación RGB.",
            category = "Accesorios"
        ),

        Product(
            id = 3,
            name = "Teclado Mecánico",
            price = 180.00,
            description = "Teclado mecánico con switches azules.",
            category = "Accesorios"
        ),

        Product(
            id = 4,
            name = "Monitor 24\"",
            price = 799.90,
            description = "Monitor Full HD ideal para trabajo y gaming.",
            category = "Pantallas"
        ),

        Product(
            id = 5,
            name = "Audífonos Bluetooth",
            price = 249.90,
            description = "Audífonos inalámbricos con cancelación de ruido.",
            category = "Audio"
        )
    )

    val cartItems = mutableListOf<CartItem>()
}