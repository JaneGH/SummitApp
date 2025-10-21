package com.example.summitapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summitapp.R
import com.example.summitapp.model.local.dao.ProductDao
import com.example.summitapp.view.adapter.ProductAdapter
import com.example.summitapp.model.local.database.AppDatabase
import com.example.summitapp.model.data.Cart
import com.example.summitapp.databinding.FragmentProductBinding
import com.example.summitapp.model.data.Product
import com.example.summitapp.model.data.Subcategory
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.repository.product.ProductRepository
import com.example.summitapp.model.repository.SubcategoryRepository
import com.google.android.material.tabs.TabLayout

class ProductFragment : Fragment() {

    private var subcategories = listOf<Subcategory>()
    private var selectedSubcategoryId: String? = null
    private lateinit var subcategoryRepository: SubcategoryRepository

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

        subcategoryRepository = SubcategoryRepository(ApiService.getInstance(), AppDatabase.getInstance(requireContext()).subcategoryDao())

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

        loadSubcategories()
        loadProducts()
    }

    private fun initDB() {
        val db = AppDatabase.getInstance(requireContext())
        productDao = db.productDao()
        productRepository = ProductRepository(ApiService.getInstance(), productDao)
    }

    private fun loadProducts() {
        Thread {
            if (!isAdded) return@Thread

            val productsFromRepo = productRepository.getProducts("")
            val filteredProducts = productsFromRepo.filter {
                (categoryId == 0 || it.categoryId == categoryId) &&
                        (selectedSubcategoryId == null || it.subcategoryId.toString() == selectedSubcategoryId)
            }

            requireActivity().runOnUiThread {
                products.clear()
                products.addAll(filteredProducts)
                productAdapter.notifyDataSetChanged()
            }
        }.start()
    }

    private fun loadSubcategories() {
        Thread {
            val loadedSubcategories = subcategoryRepository.getSubcategories(categoryId.toString())
            if (!isAdded) return@Thread

            requireActivity().runOnUiThread {
                subcategories = loadedSubcategories
                setupTabs()
            }
        }.start()
    }

    private fun setupTabs() {
        val tabLayout = binding.tabLayoutSubcategories
        tabLayout.removeAllTabs()


        subcategories.forEach { subcategory ->
            tabLayout.addTab(tabLayout.newTab().setText(subcategory.subcategoryName).setTag(subcategory.subcategoryId))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                selectedSubcategoryId = tab.tag as? String
                loadProducts()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        tabLayout.getTabAt(0)?.select()
    }

}
