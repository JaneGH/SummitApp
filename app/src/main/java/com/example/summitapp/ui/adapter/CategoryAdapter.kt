package com.example.summitapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.summitapp.R
import com.example.summitapp.data.model.Category
import com.example.summitapp.databinding.ItemCategoryBinding

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
            binding.tvTitle.text = category.categoryName
            val pathImage = "http://10.0.2.2/myshop/images/"
            val imageUrl = pathImage+category.categoryImageUrl
            Glide.with(binding.root.context)
                .load(if (!imageUrl.isNullOrEmpty()) imageUrl else R.drawable.dummy_category)
                .error(R.drawable.dummy_category)
                .into(binding.imageCategory)
            binding.root.setOnClickListener {
                onItemClick(category)
            }
        }

    }
}