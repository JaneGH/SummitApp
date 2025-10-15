package com.example.summitapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.summitapp.databinding.ItemCategoryBinding
import com.example.summitapp.model.Category

class CategoryAdapter(
    val categoryList : List<Category>,
    private val onItemClick: (Category) ->Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvTitle.text = category.title
            binding.root.setOnClickListener {
                onItemClick(category)
            }
        }

    }
}