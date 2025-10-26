package com.example.summitapp.model.repository


import com.example.summitapp.model.data.Cart
import com.example.summitapp.model.data.Product
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.request.PlaceOrderRequest
import com.example.summitapp.model.remote.request.PlaceOrderRequest.Item
import com.example.summitapp.model.remote.request.PlaceOrderRequest.DeliveryAddress
import com.example.summitapp.model.remote.response.PlaceOrderResponse
import retrofit2.Response

class CheckoutRepository(private val apiService: ApiService) {

    suspend fun placeOrder(
        userId: Int,
        deliveryAddressTitle: String,
        deliveryAddress: String,
        paymentMethod: String
    ): Response<PlaceOrderResponse> {

        val items = Cart.getCartItems().map { (product: Product, quantity: Int) ->
            Item(
                product_id = product.productId,
                quantity = quantity,
                unit_price = product.price
            )
        }

        val delivery = DeliveryAddress(
            title = deliveryAddressTitle,
            address = deliveryAddress
        )

        val request = PlaceOrderRequest(
            user_id = userId,
            delivery_address = delivery,
            items = items,
            bill_amount = Cart.getTotalPrice(),
            payment_method = paymentMethod
        )

        return apiService.placeOrder(request)
    }
}
