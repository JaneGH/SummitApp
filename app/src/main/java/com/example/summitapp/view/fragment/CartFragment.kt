package com.example.summitapp.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summitapp.model.data.Cart
import com.example.summitapp.databinding.FragmentCartBinding
import com.example.summitapp.view.adapter.CartAdapter

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(Cart.getCartItems().toMutableList()) { product, newQuantity, sign ->
            if (sign == "minus") {
                 if (newQuantity == 0) {
                    Cart.removeProduct(product)
                } else {
                    Cart.setProductQuantity(product, newQuantity)
                }
            } else {
                Cart.setProductQuantity(product, newQuantity)
            }
            updateCart()
        }

        binding.rvCart.adapter = cartAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        updateCart()
    }

    @SuppressLint("SetTextI18n")
    private fun updateCart() {
        val items = Cart.getCartItems()
        cartAdapter.updateItems(items)
        binding.tvTotal.text = "Total: ${Cart.getTotalPrice()}"
    }
}
