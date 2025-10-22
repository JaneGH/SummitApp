package com.example.summitapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.summitapp.R
import com.example.summitapp.model.data.Product
import com.example.summitapp.model.data.Cart
import com.example.summitapp.databinding.ItemProductBinding

class ProductAdapter(
    var productList:  MutableList<Product>,
    private val onQuantityChange: (Product, Int, String) -> Unit,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val quantityVisibilityMap = mutableMapOf<Int, Boolean>()

    inner class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, quantityInCart: Int, isVisible: Boolean) {
            binding.tvTitle.text = product.productName
            binding.ratingBar.rating = (product.averageRating ?: 0.0).toFloat()
            binding.tvDescription.text = product.description
            binding.tvPrice.text = product.price.toString()
            binding.etQuantity.setText(quantityInCart.toString())

            val pathImage = "http://10.0.2.2/myshop/images/"
            val imageUrl = pathImage + product.imageUrl
            Glide.with(binding.root.context)
                .load(if (!imageUrl.isNullOrEmpty()) imageUrl else R.drawable.dummy_category)
                .error(R.drawable.dummy_category)
                .into(binding.imageProduct)

            binding.btnAddToCart.visibility = if (isVisible) View.GONE else View.VISIBLE
            binding.quantityContainer.visibility = if (isVisible) View.VISIBLE else View.GONE

            binding.btnAddToCart.setOnClickListener {
                quantityVisibilityMap[adapterPosition] = true
                binding.btnAddToCart.visibility = View.GONE
                binding.quantityContainer.visibility = View.VISIBLE
                onQuantityChange(product, 1, "plus")
            }

            binding.btnPlus.setOnClickListener {
                val newQuantity = binding.etQuantity.text.toString().toInt() + 1
                binding.etQuantity.setText(newQuantity.toString())
                onQuantityChange(product, newQuantity, "plus")
            }

            binding.btnMinus.setOnClickListener {
                val newQuantity = binding.etQuantity.text.toString().toInt() - 1
                if (newQuantity >= 0) {
                    binding.etQuantity.setText(newQuantity.toString())
                    onQuantityChange(product, newQuantity, "minus")
                    if (newQuantity == 0) {
                        binding.btnAddToCart.visibility = View.VISIBLE
                        binding.quantityContainer.visibility = View.GONE
                        quantityVisibilityMap[adapterPosition] = false
                    }
                }
            }

            binding.root.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        val quantityInCart = Cart.getCartItems().find { it.first.productId == product.productId }?.second ?: 0
        val isVisible = quantityInCart > 0
        quantityVisibilityMap[position] = isVisible
        holder.bind(product, quantityInCart, isVisible)
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(newList: List<Product>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }
}
