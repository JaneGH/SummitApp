package com.example.summitapp.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summitapp.databinding.FragmentCartItemsBinding
import com.example.summitapp.view.adapter.CartItemsAdapter
import com.example.summitapp.viewmodel.CartViewModel
import androidx.fragment.app.viewModels
import com.example.summitapp.R

class CartItemsFragment : Fragment() {

    private lateinit var binding: FragmentCartItemsBinding
    private lateinit var cartItemsAdapter: CartItemsAdapter
    private val cartViewModel: CartViewModel by viewModels()

    companion object {
        val TAG = CartItemsFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartItemsAdapter = CartItemsAdapter(mutableListOf())

        binding.rvCartItems.apply {
            adapter = cartItemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        binding.btnNext.setOnClickListener {
            openPaymentFragment()
        }

        observeViewModel()
    }


    private fun openPaymentFragment() {
        val checkoutFragment = parentFragmentManager.findFragmentByTag(CheckoutFragment.TAG)
        if (checkoutFragment != null && checkoutFragment is CheckoutFragment) {
            checkoutFragment.goToPage(1)
        }
    }

    @SuppressLint("StringFormatMatches", "SetTextI18n")
    private fun observeViewModel() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartItemsAdapter.updateItems(cartItems)
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) { total ->
            binding.tvTotalSum.text = "$$total"
        }
    }
}
