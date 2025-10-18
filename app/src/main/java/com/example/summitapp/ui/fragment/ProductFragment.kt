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
import com.example.summitapp.data.local.entity.Product

class ProductFragment : Fragment() {


    private lateinit var binding : FragmentProductBinding
    private lateinit var productDao : ProductDao
    private val products = listOf(
        Product(0, 1, "Xiaomi 17", "The Xiaomi 17 is a high-performance smartphone that combines cutting-edge features with sleek design. It typically offers a powerful chipset, a high-resolution display, and an improved camera system for enhanced photography.", 200.00, "https://crdms.images.consumerreports.org/f_auto,w_1200/prod/products/cr/models/399694-smartphones-apple-iphone-11-10008711.png"),
        Product(0, 1, "Apple iPhone Air", "is a lightweight and slim smartphone, offering a balance of performance and portability. It typically features a high-resolution Retina display, Apple's powerful A-series chip, and an advanced camera system.", 200.00, "https://crdms.images.consumerreports.org/f_auto,w_1200/prod/products/cr/models/399694-smartphones-apple-iphone-11-10008711.png"),
        )

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

        val productAdapter = ProductAdapter(products) { product, quantity, sign ->
            if (sign == "minus") {
                productDao.deleteProduct(product.id)
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

    }

    private fun initDB() {
        val db = AppDatabase.getInstance(requireContext())
       productDao = db.ProductDao()
    }

}