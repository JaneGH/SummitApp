package com.example.summitapp.view.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.summitapp.R
import com.example.summitapp.databinding.FragmentCheckoutBinding
import com.example.summitapp.view.adapter.CheckoutTabsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutFragment : Fragment(R.layout.fragment_checkout) {

    lateinit var binding: FragmentCheckoutBinding
    companion object {
        val TAG = CheckoutFragment::class.java.simpleName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckoutBinding.bind(view)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "CHECKOUT"

        val checkoutTabsAdapter = CheckoutTabsAdapter(requireActivity())
        val viewPager = binding.viewPager
        viewPager.adapter = checkoutTabsAdapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Cart Items"
                1 -> tab.text = "Delivery"
                2 -> tab.text = "Payment"
                3 -> tab.text = "Summary"
            }
        }.attach()

    }

    fun goToPage(index: Int) {
        binding.viewPager.post {
            binding.viewPager.currentItem = index
        }
    }
}
