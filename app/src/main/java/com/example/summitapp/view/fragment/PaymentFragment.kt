package com.example.summitapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.summitapp.R
import com.example.summitapp.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {
    private lateinit var binding: FragmentPaymentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)
    }
}