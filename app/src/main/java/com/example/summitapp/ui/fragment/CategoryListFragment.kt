package com.example.summitapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.summitapp.R
import com.example.summitapp.data.local.database.AppDatabase
import com.example.summitapp.data.model.Category
import com.example.summitapp.databinding.FragmentCategoryLystBinding
import com.example.summitapp.data.remote.ApiService
import com.example.summitapp.data.repository.CategoryRepository
import com.example.summitapp.ui.adapter.CategoryAdapter
import com.example.summitapp.ui.viewmodel.CategoryViewModel
import com.example.summitapp.ui.viewmodel.CategoryViewModelFactory

class CategoryListFragment : Fragment() {

    private lateinit var binding: FragmentCategoryLystBinding
    private lateinit var categoriesAdapter: CategoryAdapter

    private val categories = mutableListOf<Category>()

    private val viewModel: CategoryViewModel by lazy {
        val database = AppDatabase.getInstance(requireContext())
        val repository = CategoryRepository(ApiService.getInstance(), database.CategoryDao())
        val factory = CategoryViewModelFactory(repository)
        ViewModelProvider(this, factory)[CategoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryLystBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriesAdapter = CategoryAdapter(categories) { selectedCategory ->
            openProductsFragment(selectedCategory)
        }

        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = categoriesAdapter
        }

        observeViewModel()
        viewModel.fetchCategories()
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { categoryList ->
            categories.clear()
            categories.addAll(categoryList)
            categoriesAdapter.notifyDataSetChanged()
        }
    }

    private fun openProductsFragment(category: Category) {
        val fragment = ProductFragment().apply {
            arguments = Bundle().apply {
                putInt("category_id", category.categoryId)
                putString("category_title", category.categoryName)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
