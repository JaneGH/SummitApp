package com.example.summitapp.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.summitapp.R
import com.example.summitapp.model.data.Category
import com.example.summitapp.databinding.FragmentCategoryLystBinding
import com.example.summitapp.view.adapter.CategoryAdapter
import com.example.summitapp.viewmodel.CategoryViewModel
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels

import kotlin.getValue

class CategoryListFragment : Fragment() {

    private var _binding: FragmentCategoryLystBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoriesAdapter: CategoryAdapter
    private val categories = mutableListOf<Category>()

    private val viewModel: CategoryViewModel by viewModels { CategoryViewModel.Factory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryLystBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "SUPER CART"
        setupRecyclerView()
        observeViewModel()
        viewModel.fetchCategories()
        setupSearch()
        setupMenu()
    }

    private fun setupRecyclerView() {
        categoriesAdapter = CategoryAdapter(categories) { selectedCategory ->
            openProductsFragment(selectedCategory)
        }

        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = categoriesAdapter
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCategories(s.toString())
            }
        })
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)

                val searchItem = menu.findItem(R.id.action_search)
                searchItem.setOnMenuItemClickListener {
                    toggleSearchBar()
                    true
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun toggleSearchBar() {
        if (binding.tilSearch.isVisible) {
            binding.tilSearch.visibility = View.GONE
            binding.etSearch.setText("")
            binding.etSearch.clearFocus()
            filterCategories("")
        } else {
            binding.tilSearch.visibility = View.VISIBLE
            binding.etSearch.requestFocus()
        }
    }

    private fun filterCategories(query: String?) {
        if (query.isNullOrBlank()) {
            categoriesAdapter.updateList(categories)
        } else {
            val filtered = categories.filter {
                it.categoryName.contains(query, ignoreCase = true)
            }
            categoriesAdapter.updateList(filtered)
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
