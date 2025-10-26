package com.example.summitapp.view.fragment

import com.example.summitapp.R
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.summitapp.Constants
import com.example.summitapp.databinding.FragmentDeliveryBinding
import com.example.summitapp.model.data.Address
import com.example.summitapp.viewmodel.DeliveryViewModel

class DeliveryFragment : Fragment() {

    companion object {
        val TAG = DeliveryFragment::class.java.simpleName
    }
    private lateinit var binding: FragmentDeliveryBinding
    private val viewModel: DeliveryViewModel by viewModels { DeliveryViewModel.Factory }

    private var selectedAddress: Address? = null
    private var userId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = getUserIdFromPrefs()

        observeViewModel()

        if (userId != null) {
            viewModel.fetchAddresses(userId!!)
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }

        binding.btnNext.setOnClickListener {
            if (selectedAddress == null) {
                Toast.makeText(requireContext(), "Please select an address", Toast.LENGTH_SHORT).show()
            } else {
                openPaymentFragment()
            }
        }

        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog()
        }
    }

    private fun observeViewModel() {

        viewModel.addresses.observe(viewLifecycleOwner) { list ->
            showAddresses(list)
        }

        viewModel.addressAdded.observe(viewLifecycleOwner) { added ->
            if (added) {
                Toast.makeText(requireContext(), "Address added successfully", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showAddresses(addresses: List<Address>) {
        binding.radioGroupAddresses.removeAllViews()
        for (address in addresses) {
            val radioButton = com.google.android.material.radiobutton.MaterialRadioButton(requireContext()).apply {
                text = "${address.title} - ${address.address}"
                id = View.generateViewId()
                setPadding(16, 16, 16, 16)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 8
                    bottomMargin = 8
                }
                background = resources.getDrawable(R.drawable.radio_button_border, null)
                gravity = android.view.Gravity.START or android.view.Gravity.CENTER_VERTICAL
                buttonTintList = resources.getColorStateList(R.color.colorPrimary, null)
                layoutDirection = View.LAYOUT_DIRECTION_RTL
            }

            radioButton.setOnClickListener {
                selectedAddress = address
            }

            binding.radioGroupAddresses.addView(radioButton)
        }
    }


    private fun showAddAddressDialog() {
        val context = requireContext()
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 32, 48, 8)
        }

        val etTitle = EditText(context).apply { hint = "Title (Home, Office)" }
        val etAddress = EditText(context).apply { hint = "Full Address" }

        layout.addView(etTitle)
        layout.addView(etAddress)

        AlertDialog.Builder(context)
            .setTitle("Add New Address")
            .setView(layout)
            .setPositiveButton("Save") { dialog, _ ->
                val title = etTitle.text.toString().trim()
                val addressText = etAddress.text.toString().trim()

                if (title.isEmpty() || addressText.isEmpty()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    userId?.let { viewModel.addAddress(it, title, addressText) }
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun openPaymentFragment() {
        val checkoutFragment = parentFragmentManager.findFragmentByTag(CheckoutFragment.TAG)
        if (checkoutFragment != null && checkoutFragment is CheckoutFragment) {
            checkoutFragment.goToPage(2)
        }
    }
    private fun getUserIdFromPrefs(): Int? {
        val pref = requireContext().getSharedPreferences(Constants.SETTING, Context.MODE_PRIVATE)
        val userIdStr = pref.getString(Constants.USER_ID, null)
        return userIdStr?.toIntOrNull()
    }
}
