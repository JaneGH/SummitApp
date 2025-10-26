package com.example.summitapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summitapp.R
import com.example.summitapp.databinding.FragmentProductBinding
import com.example.summitapp.model.data.Cart
import com.example.summitapp.model.data.Subcategory
import com.example.summitapp.view.adapter.ProductAdapter
import com.example.summitapp.viewmodel.ProductViewModel
import com.google.android.material.tabs.TabLayout

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels { ProductViewModel.Factory }
    private lateinit var productAdapter: ProductAdapter
    private var categoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { categoryId = it.getInt("category_id", 0) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "SUPER CART"
        setupRecyclerView()
        observeViewModel()
        viewModel.setCategoryId(categoryId, viewLifecycleOwner)
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            mutableListOf(),
            onQuantityChange = { product, quantity, sign ->
                if (sign == "minus" && quantity == 0) Cart.removeProduct(product)
                else Cart.setProductQuantity(product, quantity)
                val index = productAdapter.productList.indexOfFirst { it.productId == product.productId }
                if (index >= 0) productAdapter.notifyItemChanged(index)
            },
            onItemClick = { product ->
                val fragment = ProductDetailsFragment().apply {
                    arguments = Bundle().apply { putInt("product_id", product.productId) }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
        binding.rvProducts.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateList(products)
        }
        viewModel.subcategories.observe(viewLifecycleOwner) { subcategories ->
            setupTabs(subcategories)
        }
    }

    private fun setupTabs(subcategories: List<Subcategory>) {
        val tabLayout = binding.tabLayoutSubcategories
        tabLayout.removeAllTabs()
        if (subcategories.isEmpty()) return
        subcategories.forEach { sub ->
            tabLayout.addTab(tabLayout.newTab().setText(sub.subcategoryName).setTag(sub.subcategoryId))
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val subcategoryId = (tab.tag as? String)?.toIntOrNull()
                viewModel.selectSubcategory(subcategoryId)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        tabLayout.getTabAt(0)?.let { firstTab ->
            tabLayout.selectTab(firstTab)
            val subcategoryId = (firstTab.tag as? String)?.toIntOrNull()
            viewModel.selectSubcategory(subcategoryId)
        }
    }
}
