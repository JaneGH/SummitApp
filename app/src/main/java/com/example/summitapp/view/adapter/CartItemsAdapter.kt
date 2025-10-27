package com.example.summitapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.summitapp.Constants.BASE_URL
import com.example.summitapp.R
import com.example.summitapp.databinding.ItemProductBinding
import com.example.summitapp.model.data.CartItem
import com.example.summitapp.databinding.ItemProductCartBinding
import com.example.summitapp.model.data.Product
import com.example.summitapp.view.adapter.CartAdapter.CartViewHolder

class CartItemsAdapter(
    private val cartItems: MutableList<Pair<Product, Int>>,
) : RecyclerView.Adapter<CartItemsAdapter.CartItemViewHolder>() {

    inner class CartItemViewHolder(val binding: ItemProductCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, quantity: Int) {

            binding.tvTitle.text = product.productName
            binding.tvUnitPriceValue.text = product.price.toString()
            binding.tvQuantityValue.text = quantity.toString()
            binding.tvAmountValue.text = (product.price * quantity).toString()

            val pathImage = "${BASE_URL}/images/"
            val imageUrl = pathImage + product.imageUrl
            Glide.with(binding.root.context)
                .load(if (imageUrl.isNotEmpty()) imageUrl else R.drawable.dummy_category)
                .error(R.drawable.dummy_category)
                .into(binding.imageProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding = ItemProductCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
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
