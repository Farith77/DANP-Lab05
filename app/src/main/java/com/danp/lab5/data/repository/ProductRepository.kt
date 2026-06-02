package com.danp.lab5.data.repository

import com.danp.lab5.data.local.ProductDataSource
import com.danp.lab5.data.model.Product

class ProductRepository(private val dataSource: ProductDataSource) {
    fun getProducts(): List<Product> = dataSource.getProducts()

    fun getProductById(id: Int): Product? = dataSource.getProducts().find { it.id == id }
}
