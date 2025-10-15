package com.example.summitapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.summitapp.R
import com.example.summitapp.adapters.CategoryAdapter
import com.example.summitapp.databinding.FragmentCategoryLystBinding
import com.example.summitapp.model.Category

class CategoryListFragment : Fragment() {


    private lateinit var binding : FragmentCategoryLystBinding
    private val categories = listOf(
        Category(1, "Smart Phones"),
        Category(2, "Laptops"),
        Category(3, "Drinks", "https://en.wikipedia.org/wiki/Coca-Cola#/media/File:Coca_Cola_Flasche_-_Original_Taste.jpg"),
        Category(4, "Soft Drinks", "https://en.wikipedia.org/wiki/Coca-Cola#/media/File:Coca_Cola_Flasche_-_Original_Taste.jpg"),
        Category(1, "Kids Wear"),
        Category(2, "Grocery")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryLystBinding.inflate(inflater, container, false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CategoryAdapter(categories){ selectedCategory ->
            openProductsFragment(selectedCategory)
        }
        binding.rvCategories.adapter = adapter
        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(),2)
    }

    private fun openProductsFragment(category: Category) {
        val fragment = ProductFragment()

        val bundle = Bundle()
        bundle.putLong("category_id", category.id)
        bundle.putString("category_title", category.title)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}