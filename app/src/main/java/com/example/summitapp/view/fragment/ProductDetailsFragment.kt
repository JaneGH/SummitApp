package com.example.summitapp.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.summitapp.Constants.BASE_URL
import com.example.summitapp.R
import com.example.summitapp.model.data.Cart
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.response.ProductDetailsResponse
import com.example.summitapp.databinding.FragmentProductDetailsBinding
import com.example.summitapp.model.data.Product
import com.example.summitapp.view.adapter.ImageSliderAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsFragment : Fragment() {

    private var productId: Int = 0
    private lateinit var binding: FragmentProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getInt("product_id", 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Details"
        loadProductDetails()
    }

    private fun setImagesForProduct(product: Product) {
        var imageUrls = product.images
            ?.sortedBy { it.displayOrder }
            ?.map { imageObj -> "${BASE_URL}/images/${imageObj.image}" }
            ?: emptyList()

        if (imageUrls.isEmpty() && !product.imageUrl.isNullOrEmpty()){
            imageUrls = listOf("${BASE_URL}/images/${product.imageUrl}")
        }
        binding.viewPagerImages.adapter = ImageSliderAdapter(imageUrls)

        binding.dotsIndicator.attachTo(binding.viewPagerImages)

    }

    private fun loadProductDetails() {
        val api = ApiService.getInstance()
        api.getProductDetails(productId).enqueue(object : Callback<ProductDetailsResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ProductDetailsResponse>,
                response: Response<ProductDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    val product = response.body()?.product ?: return

                    setImagesForProduct(product)
                    binding.tvTitle.text = product.productName
                    binding.tvDescription.text = product.description
                    binding.tvPrice.text = "${product.price}"

                    //specifications
                    val specs = product.specifications
                    if (!specs.isNullOrEmpty()) {
                        binding.tvSpecificationsTitle.visibility = View.VISIBLE
                        binding.specDivider.visibility = View.VISIBLE
                        binding.specificationsContainer.visibility = View.VISIBLE
                        binding.specificationsContainer.removeAllViews()

                        specs.sortedBy { it.displayOrder }.forEach { spec ->
                            val row = TableRow(requireContext())

                            val titleView = TextView(requireContext()).apply {
                                text = spec.title
                                textSize = 14f
                                setPadding(8, 8, 8, 8)
                            }

                            val valueView = TextView(requireContext()).apply {
                                text = spec.specification
                                textSize = 14f
                                setPadding(8, 8, 8, 8)
                            }

                            row.addView(titleView)
                            row.addView(valueView)

                            binding.specificationsContainer.addView(row)
                        }
                    }

                    val quantityInCart = Cart.getCartItems()
                        .find { it.first.productId == product.productId }?.second ?: 0
                    binding.etQuantity.setText(quantityInCart.toString())

                    if (quantityInCart > 0) {
                        binding.btnAddToCart.visibility = View.GONE
                        binding.quantityContainer.visibility = View.VISIBLE
                    } else {
                        binding.btnAddToCart.visibility = View.VISIBLE
                        binding.quantityContainer.visibility = View.GONE
                    }

                    binding.btnAddToCart.setOnClickListener {
                        val newQuantity = 1
                        binding.etQuantity.setText(newQuantity.toString())
                        Cart.setProductQuantity(product, newQuantity)

                        binding.btnAddToCart.visibility = View.GONE
                        binding.quantityContainer.visibility = View.VISIBLE
                    }


                    binding.btnPlus.setOnClickListener {
                        val newQuantity = binding.etQuantity.text.toString().toInt() + 1
                        binding.etQuantity.setText(newQuantity.toString())
                        Cart.setProductQuantity(product, newQuantity)
                    }

                    binding.btnMinus.setOnClickListener {
                        val newQuantity = binding.etQuantity.text.toString().toInt() - 1
                        if (newQuantity >= 0) {
                            binding.etQuantity.setText(newQuantity.toString())
                            Cart.setProductQuantity(product, newQuantity)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<ProductDetailsResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
