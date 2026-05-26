package com.danp.lab5.data.repository

import com.danp.lab5.data.model.Product

object ProductRepository {
    private val products = listOf(
        Product(
            id = 1,
            name = "Laptop Gamer",
            price = 3500.00,
            description = "Laptop de alto rendimiento para juegos y programación.",
            category = "Tecnología",
            imageUrl = "https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400"
        ),
        Product(
            id = 2,
            name = "Mouse RGB",
            price = 85.50,
            description = "Mouse ergonómico con iluminación RGB.",
            category = "Accesorios",
            imageUrl = "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400"
        ),
        Product(
            id = 3,
            name = "Teclado Mecánico",
            price = 180.00,
            description = "Teclado mecánico con switches azules.",
            category = "Accesorios",
            imageUrl = "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=400"
        ),
        Product(
            id = 4,
            name = "Monitor 24\"",
            price = 799.90,
            description = "Monitor Full HD ideal para trabajo y gaming.",
            category = "Pantallas",
            imageUrl = "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400"
        ),
        Product(
            id = 5,
            name = "Audífonos Bluetooth",
            price = 249.90,
            description = "Audífonos inalámbricos con cancelación de ruido.",
            category = "Audio",
            imageUrl = "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400"
        )
    )

    fun getProducts(): List<Product> = products

    fun getProductById(id: Int): Product? = products.find { it.id == id }
}
