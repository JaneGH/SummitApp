package com.example.summitapp.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.summitapp.view.fragment.CartItemsFragment
import com.example.summitapp.view.fragment.DeliveryFragment
import com.example.summitapp.view.fragment.PaymentFragment
import com.example.summitapp.view.fragment.SummaryFragment

class CheckoutTabsAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CartItemsFragment()
            1 -> DeliveryFragment()
            2 -> PaymentFragment()
            3 -> SummaryFragment()
            else -> CartItemsFragment()
        }
    }
}
