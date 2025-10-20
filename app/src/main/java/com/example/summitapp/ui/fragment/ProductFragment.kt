package com.example.summitapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summitapp.R
import com.example.summitapp.data.local.dao.ProductDao
import com.example.summitapp.ui.adapter.ProductAdapter
import com.example.summitapp.data.local.database.AppDatabase
import com.example.summitapp.data.model.Cart
import com.example.summitapp.databinding.FragmentProductBinding
import com.example.summitapp.data.model.Product
import com.example.summitapp.data.remote.ApiService
import com.example.summitapp.data.repository.ProductRepository

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var productDao: ProductDao
    private lateinit var productRepository: ProductRepository
    private lateinit var productAdapter: ProductAdapter

    private val products = mutableListOf<Product>()
    private var categoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getInt("category_id", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        initDB()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(
            products,
            { product, quantity, sign ->
                if (sign == "minus" && quantity == 0) {
                    Cart.removeProduct(product)
                } else {
                    Cart.setProductQuantity(product, quantity)
                }

                val index = products.indexOf(product)
                if (index != -1) productAdapter.notifyItemChanged(index)
            },
            { product ->
                 val fragment = ProductDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putInt("product_id", product.productId)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        )

        binding.rvProducts.adapter = productAdapter
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvProducts.addItemDecoration(itemDecoration)

        loadProducts()
    }

    private fun initDB() {
        val db = AppDatabase.getInstance(requireContext())
        productDao = db.ProductDao()
        productRepository = ProductRepository(ApiService.getInstance(), productDao)
    }

    private fun loadProducts() {
        Thread {
            if (!isAdded) return@Thread
            val productsFromRepo = productRepository.getProducts("")
            val filteredProducts = if (categoryId != 0) {
                productsFromRepo.filter { it.categoryId == categoryId }
            } else {
                productsFromRepo
            }

            requireActivity().runOnUiThread {
                products.clear()
                products.addAll(filteredProducts)
                productAdapter.notifyDataSetChanged()
            }
        }.start()
    }
}
