package com.example.summitapp.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summitapp.Constants
import com.example.summitapp.R
import com.example.summitapp.databinding.FragmentSummaryBinding
import com.example.summitapp.model.data.Cart
import com.example.summitapp.view.adapter.CartItemsAdapter
import com.example.summitapp.viewmodel.CheckoutViewModel

class SummaryFragment : Fragment(R.layout.fragment_summary) {
    private lateinit var binding: FragmentSummaryBinding
    private lateinit var cartItemsAdapter: CartItemsAdapter

    private val checkoutViewModel: CheckoutViewModel by activityViewModels { CheckoutViewModel.Factory }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSummaryBinding.bind(view)

        cartItemsAdapter = CartItemsAdapter(Cart.getCartItems().toMutableList())

        binding.rvCartItemsSummary.apply {
            adapter = cartItemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        checkoutViewModel.selectedAddress.observe(viewLifecycleOwner) { address ->
            if (address != null) {
                binding.tvDeliveryAddressTitle.text = address.title
                binding.tvDeliveryAddress.text = address.address
            } else {
                binding.tvDeliveryAddressTitle.text = "No address selected"
                binding.tvDeliveryAddress.text = ""
            }
        }

        checkoutViewModel.selectedPayment.observe(viewLifecycleOwner) { payment ->
            binding.tvPaymentMethod.text = payment?.displayName ?: "No payment selected"
        }

        binding.btnPlaceOrder.setOnClickListener {
            checkoutViewModel.placeOrder(getUserIdFromPrefs())
        }

        binding.tvTotalSum.text = "$${Cart.getTotalPrice()}"

        checkoutViewModel.orderStatus.observe(viewLifecycleOwner) { result ->
            result.onSuccess { orderId ->
                Toast.makeText(requireContext(), "Order placed: $orderId", Toast.LENGTH_SHORT).show()
            }
            result.onFailure { e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getUserIdFromPrefs(): Int? {
        val pref = requireContext().getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE)
        val userIdStr = pref.getString(Constants.USER_ID, null)
        return userIdStr?.toIntOrNull()
    }
}
