
package com.example.summitapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.summitapp.model.data.Cart
import com.example.summitapp.model.data.Product

class CartViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<List<Pair<Product, Int>>>(Cart.getCartItems())
    val cartItems: LiveData<List<Pair<Product, Int>>> get() = _cartItems

    private val _totalPrice = MutableLiveData<Double>(Cart.getTotalPrice())
    val totalPrice: LiveData<Double> get() = _totalPrice

    fun updateProductQuantity(product: Product, newQuantity: Int, sign: String) {
        if (sign == "minus") {
            if (newQuantity == 0) {
                Cart.removeProduct(product)
            } else {
                Cart.setProductQuantity(product, newQuantity)
            }
        } else {
            Cart.setProductQuantity(product, newQuantity)
        }
        refreshCart()
    }

    private fun refreshCart() {
        _cartItems.value = Cart.getCartItems()
        _totalPrice.value = Cart.getTotalPrice()
    }
}
