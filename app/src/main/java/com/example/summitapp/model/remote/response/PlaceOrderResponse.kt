package com.example.summitapp.model.remote.response

data class PlaceOrderResponse(
    val status: Int,
    val message: String,
    val order_id: Int?
)