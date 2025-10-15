package com.example.summitapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.summitapp.databinding.ItemProductBinding
import com.example.summitapp.model.Product

class ProductAdapter(
    val productList : List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
       val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
       return productList.size
    }

    inner class ProductViewHolder(private val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product) {
            binding.tvTitle.text = product.title
            binding.tvDescription.text = product.description
            binding.tvPrice.text = product.price.toString()
        }
    }

}