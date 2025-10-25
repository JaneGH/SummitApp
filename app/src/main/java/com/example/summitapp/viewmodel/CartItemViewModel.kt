package com.example.summitapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.summitapp.model.data.CartItem
import com.example.summitapp.model.data.Product

class CartItemViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<CartItem>()
    val cartItems: LiveData<CartItem> get() = _cartItems

    private val _totalAmount = MutableLiveData<Double>()
    val totalAmount: LiveData<Double> get() = _totalAmount

    fun setCartItem(product: Product, quantity: Int) {
        _cartItems.value = CartItem(product, quantity)
        updateTotalAmount()
    }

    fun updateQuantity(newQuantity: Int) {
        _cartItems.value?.quantity = newQuantity
        updateTotalAmount()
    }

    private fun updateTotalAmount() {
        _cartItems.value?.let {
            _totalAmount.value = it.product.price * it.quantity
        }
    }
}
