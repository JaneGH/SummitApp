package com.example.summitapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summitapp.adapters.CategoryAdapter
import com.example.summitapp.adapters.ProductAdapter
import com.example.summitapp.databinding.FragmentProductBinding
import com.example.summitapp.model.Product

class ProductFragment : Fragment() {


    private lateinit var binding : FragmentProductBinding
    private val products = listOf(
        Product(1, 1, "Phone1", "Phone1", 200.00, null),
        Product(2, 2, "Phone2", "Phone2", 150.00, null),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productAdapter = ProductAdapter(products)

        binding.rvProducts.adapter = productAdapter
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
    }

}