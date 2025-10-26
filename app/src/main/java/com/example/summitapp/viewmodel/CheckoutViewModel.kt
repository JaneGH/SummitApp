package com.example.summitapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.summitapp.SummitApp
import com.example.summitapp.model.data.Address
import com.example.summitapp.model.data.Cart
import com.example.summitapp.model.data.PaymentMethod
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.repository.CheckoutRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CheckoutViewModel(
    application: Application,
    private val repository: CheckoutRepository
) : ViewModel() {

    private val _selectedAddress = MutableLiveData<Address?>()
    val selectedAddress: LiveData<Address?> get() = _selectedAddress

    private val _selectedPayment = MutableLiveData<PaymentMethod?>()
    val selectedPayment: LiveData<PaymentMethod?> get() = _selectedPayment

    private val _orderStatus = MutableLiveData<Result<String>>()
    val orderStatus: LiveData<Result<String>> get() = _orderStatus


    fun setAddress(address: Address) {
        _selectedAddress.value = address
    }

    fun setPayment(payment: PaymentMethod) {
        _selectedPayment.value = payment
    }


    fun placeOrder(userId: Int?) {
        val address = _selectedAddress.value
        val payment = _selectedPayment.value
        val cartItems = Cart.getCartItems()

        if (userId==null|| address == null || payment == null || cartItems.isEmpty()) {
            _orderStatus.postValue(Result.failure(Exception("Incomplete order")))
            return
        }

        viewModelScope.launch {
            try {
                val response = repository.placeOrder(
                    userId = userId,
                    deliveryAddressTitle = address.title,
                    deliveryAddress = address.address,
                    paymentMethod = payment.code
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == 0) {
                        _orderStatus.postValue(Result.success(body.order_id.toString()))
                        Cart.clearCart()
                    } else {
                        _orderStatus.postValue(
                            Result.failure(Exception(body?.message ?: "Unknown error"))
                        )
                    }
                } else {
                    _orderStatus.postValue(Result.failure(HttpException(response)))
                }
            } catch (e: IOException) {
                _orderStatus.postValue(Result.failure(Exception("Network error: ${e.message}")))
            } catch (e: Exception) {
                _orderStatus.postValue(Result.failure(e))
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SummitApp)
                val repository = CheckoutRepository(ApiService.getInstance())
                CheckoutViewModel(application, repository)
            }
        }
    }
}