package com.example.summitapp.model.data

object Cart {

    private val cartItems = mutableListOf<Pair<Product, Int>>()

    fun addProduct(product: Product, quantity: Int = 1) {
        val index = cartItems.indexOfFirst { it.first.productId == product.productId }
        if (index != -1) {
            val current = cartItems[index]
            cartItems[index] = current.copy(second = current.second + quantity)
        } else {
            cartItems.add(product to quantity)
        }
    }

    fun removeProduct(product: Product) {
        val index = cartItems.indexOfFirst { it.first.productId == product.productId }
        if (index != -1) {
            val current = cartItems[index]
            if (current.second > 1) {
                cartItems[index] = current.copy(second = current.second - 1)
            } else {
                cartItems.removeAt(index)
            }
        }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getCartItems(): List<Pair<Product, Int>> = cartItems.toList()

    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.first.price * it.second }
    }

    fun setProductQuantity(product: Product, quantity: Int) {
        val index = cartItems.indexOfFirst { it.first.productId == product.productId }
        if (index != -1) {
            if (quantity <= 0) {
                cartItems.removeAt(index)
            } else {
                cartItems[index] = product to quantity
            }
        } else if (quantity > 0) {
            cartItems.add(product to quantity)
        }
    }
}
