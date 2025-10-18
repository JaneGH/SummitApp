package com.example.summitapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summitapp.data.local.dao.ProductDao
import com.example.summitapp.ui.adapter.ProductAdapter
import com.example.summitapp.data.local.database.AppDatabase
import com.example.summitapp.databinding.FragmentProductBinding
import com.example.summitapp.data.model.Product
import com.example.summitapp.data.remote.ApiService
import com.example.summitapp.data.repository.ProductRepository

class ProductFragment : Fragment() {


    private lateinit var binding : FragmentProductBinding
    private lateinit var productDao : ProductDao

    private lateinit var productRepository: ProductRepository

    private lateinit var productAdapter: ProductAdapter

    private val products = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        initDB()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(products) { product, quantity, sign ->
            if (sign == "minus") {
                productDao.deleteProduct(product.productId)
                println("!!! ${productDao.getAllProducts().size}")
            } else {
                productDao.insertProduct(product)
                println("!!! ${productDao.getAllProducts().size}")
            }
        }

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
            requireActivity().runOnUiThread {
                products.clear()
                products.addAll(productsFromRepo)
                productAdapter.notifyDataSetChanged()
            }
        }.start()
    }

}