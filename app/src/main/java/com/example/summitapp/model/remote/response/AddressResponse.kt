package com.example.summitapp.model.remote.response

import com.example.summitapp.model.data.Address

data class AddressResponse(
    val status: Int,
    val message: String,
    val addresses: List<Address>
)