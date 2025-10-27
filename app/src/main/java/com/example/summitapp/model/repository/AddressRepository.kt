package com.example.summitapp.model.repository

import com.example.summitapp.model.data.Address
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.request.AddressRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AddressRepository(private val apiService: ApiService) {

    suspend fun fetchAddresses(userId: Int): List<Address> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getUserAddresses(userId)
            if (response.isSuccessful) {
                response.body()?.addresses ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addAddress(userId: Int, title: String, address: String): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val request = AddressRequest(
                    user_id = userId,
                    title = title,
                    address = address
                )

                val response = apiService.addUserAddress(request)
                response.isSuccessful

            } catch (e: HttpException) {
                e.printStackTrace()
                false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
}