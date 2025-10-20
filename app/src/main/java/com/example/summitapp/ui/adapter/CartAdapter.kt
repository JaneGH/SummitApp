package com.example.summitapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.summitapp.R
import com.example.summitapp.data.model.Product
import com.example.summitapp.databinding.ItemProductBinding

class CartAdapter(
    private val cartItems: MutableList<Pair<Product, Int>>,
    private val onQuantityChange: (Product, Int, String) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, quantity: Int) {
            binding.tvTitle.text = product.productName
            binding.tvDescription.text = product.description
            binding.tvPrice.text = product.price.toString()
            binding.etQuantity.setText(quantity.toString())

            val pathImage = "http://10.0.2.2/myshop/images/"
            val imageUrl = pathImage + product.imageUrl
            Glide.with(binding.root.context)
                .load(if (!imageUrl.isNullOrEmpty()) imageUrl else R.drawable.dummy_category)
                .error(R.drawable.dummy_category)
                .into(binding.imageProduct)


            binding.btnAddToCart.visibility = View.GONE
            binding.quantityContainer.visibility = View.VISIBLE

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
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val (product, quantity) = cartItems[position]
        holder.bind(product, quantity)
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateItems(newItems: List<Pair<Product, Int>>) {
        cartItems.clear()
        cartItems.addAll(newItems)
        notifyDataSetChanged()
    }
}
