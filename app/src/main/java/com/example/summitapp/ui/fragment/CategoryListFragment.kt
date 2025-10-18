package com.example.summitapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommersproject.model.Category
import com.example.summitapp.R
import com.example.summitapp.data.local.dao.CategoryDao
import com.example.summitapp.data.local.database.AppDatabase
import com.example.summitapp.ui.adapter.CategoryAdapter
import com.example.summitapp.databinding.FragmentCategoryLystBinding
import com.example.summitapp.data.remote.ApiService
import com.example.summitapp.data.repository.CategoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryListFragment : Fragment() {


    private lateinit var binding : FragmentCategoryLystBinding
    private lateinit var categoryRepository: CategoryRepository

    private lateinit var categoriesAdapter: CategoryAdapter

    private val categories = mutableListOf<Category>()
//    private val categories = listOf(
//        Category(1, "Smart Phones"),
//        Category(2, "Laptops"),
//        Category(3, "Drinks", "https://en.wikipedia.org/wiki/Coca-Cola#/media/File:Coca_Cola_Flasche_-_Original_Taste.jpg"),
//        Category(4, "Soft Drinks", "https://en.wikipedia.org/wiki/Coca-Cola#/media/File:Coca_Cola_Flasche_-_Original_Taste.jpg"),
//        Category(1, "Kids Wear"),
//        Category(2, "Grocery")
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryLystBinding.inflate(inflater, container, false)
        val database = AppDatabase.getInstance(requireContext())
        val categoryDao = database.CategoryDao()
        categoryRepository = CategoryRepository(ApiService.getInstance(), categoryDao)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesAdapter = CategoryAdapter(categories){ selectedCategory ->
            openProductsFragment(selectedCategory)
        }
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(),2)
        loadCategories()

    }

    private fun openProductsFragment(category: Category) {
        val fragment = ProductFragment()

        val bundle = Bundle()
        bundle.putInt("category_id", category.categoryId)
        bundle.putString("category_title", category.categoryName)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun loadCategories() {
        Thread {
            if (!isAdded) return@Thread
            val categoriesFromRepo = categoryRepository.getCategories()
            requireActivity().runOnUiThread {
                categories.clear()
                categories.addAll(categoriesFromRepo)
                categoriesAdapter.notifyDataSetChanged()
            }
        }.start()
    }
}