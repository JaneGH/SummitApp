package com.example.summitapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.summitapp.SummitApp
import com.example.summitapp.model.data.Address
import com.example.summitapp.model.repository.AddressRepository
import com.example.summitapp.model.remote.ApiService
import kotlinx.coroutines.launch

class DeliveryViewModel(
    application: Application,
    private val repository: AddressRepository
) : ViewModel() {

    val addresses = MutableLiveData<List<Address>>()
    val addressAdded = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun fetchAddresses(userId: Int) {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val result = repository.fetchAddresses(userId)
                addresses.postValue(result)
            } catch (e: Exception) {
                error.postValue("Failed to load addresses")
            } finally {
                loading.postValue(false)
            }
        }
    }

    fun addAddress(userId: Int, title: String, address: String) {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val success = repository.addAddress(userId, title, address)
                if (success) {
                    addressAdded.postValue(true)
                    fetchAddresses(userId)
                } else {
                    error.postValue("Failed to add address")
                }
            } catch (e: Exception) {
                error.postValue("Error: ${e.localizedMessage}")
            } finally {
                loading.postValue(false)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SummitApp)
                val repository = AddressRepository(ApiService.getInstance())
                DeliveryViewModel(application, repository)
            }
        }
    }
}
