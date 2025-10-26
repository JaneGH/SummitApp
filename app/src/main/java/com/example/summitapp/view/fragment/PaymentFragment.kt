package com.example.summitapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.summitapp.R
import com.example.summitapp.databinding.FragmentPaymentBinding
import com.example.summitapp.model.data.PaymentMethod
import com.example.summitapp.viewmodel.CheckoutViewModel

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    companion object {
        val TAG = PaymentFragment::class.java.simpleName
    }

    private val checkoutViewModel: CheckoutViewModel by activityViewModels { CheckoutViewModel.Factory }
    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            val selectedPaymentMethod = getSelectedPaymentMethod()
            if (selectedPaymentMethod == null) {
                Toast.makeText(requireContext(), "Please select a payment method", Toast.LENGTH_SHORT).show()
            } else {
                checkoutViewModel.setPayment(selectedPaymentMethod)
                openSummaryFragment()
            }
        }
    }


    private fun getSelectedPaymentMethod(): PaymentMethod? {
        val selectedRadioButtonId = binding.radioGroupPaymentMethods.checkedRadioButtonId
        val selectedRadioButton = binding.root.findViewById<RadioButton>(selectedRadioButtonId)

        return when (selectedRadioButton?.text) {
            PaymentMethod.COD.displayName -> PaymentMethod.COD
            PaymentMethod.CreditCard.displayName -> PaymentMethod.CreditCard
            PaymentMethod.PayPal.displayName -> PaymentMethod.PayPal
            PaymentMethod.InternetBanking.displayName -> PaymentMethod.InternetBanking
            else -> null
        }
    }

    private fun openSummaryFragment() {
        val checkoutFragment = parentFragmentManager.findFragmentByTag(CheckoutFragment.TAG)
        if (checkoutFragment != null && checkoutFragment is CheckoutFragment) {
            checkoutFragment.goToPage(3)
        }
    }
}