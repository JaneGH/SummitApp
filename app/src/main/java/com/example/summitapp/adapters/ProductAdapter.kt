package com.example.summitapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.summitapp.databinding.ItemProductBinding
import com.example.summitapp.data.local.entity.Product

class ProductAdapter(
    val productList : List<Product>,
    private val onQuantityChange: (Product, Int, String) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    private val quantityVisibilityMap = mutableMapOf<Int, Boolean>()

    inner class ProductViewHolder(val binding : ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product) {
            binding.tvTitle.text = product.title
            binding.tvDescription.text = product.description
            binding.tvPrice.text = product.price.toString()
            Glide.with(binding.root.context)
                .load(product.imageUrl)
                .into(binding.imageProduct)
        }
    }
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
        val position = holder.bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            val isQuantityVisible = quantityVisibilityMap[position] ?: false

            val product = productList[position]
            holder.bind(product)
            holder.binding.btnAddToCart.visibility =
                if (isQuantityVisible) View.GONE else View.VISIBLE
            holder.binding.quantityContainer.visibility =
                if (isQuantityVisible) View.VISIBLE else View.GONE

            holder.binding.btnAddToCart.setOnClickListener {
                quantityVisibilityMap[position] = true
                notifyItemChanged(position)
            }


            holder.binding.btnPlus.setOnClickListener {
                val newQuantity = holder.binding.etQuantity.text.toString().toInt() + 1
                holder.binding.etQuantity.setText(newQuantity.toString())
                onQuantityChange(product, newQuantity, "plus")
              }

            holder.binding.btnMinus.setOnClickListener {
                val newQuantity = holder.binding.etQuantity.text.toString().toInt() - 1
                if (newQuantity >= 0) {
                    holder.binding.etQuantity.setText(newQuantity.toString())
                    onQuantityChange(product, newQuantity,"minus")
                }
            }
        }
    }

    override fun getItemCount(): Int {
       return productList.size
    }

}