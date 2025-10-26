package com.example.summitapp.model.remote.request

data class PlaceOrderRequest(
    val user_id: Int,
    val delivery_address: DeliveryAddress,
    val items: List<Item>,
    val bill_amount: Double,
    val payment_method: String
) {
    data class DeliveryAddress(
        val title: String,
        val address: String
    )

    data class Item(
        val product_id: Int,
        val quantity: Int,
        val unit_price: Double
    )
}