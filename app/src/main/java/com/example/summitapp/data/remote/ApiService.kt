package com.example.summitapp.data.remote
import com.example.summitapp.data.remote.request.LoginRequest
import com.example.summitapp.data.remote.request.RegisterRequest
import com.example.summitapp.data.remote.response.CategoryResponse
import com.example.summitapp.data.remote.response.LoginResponse
import com.example.summitapp.data.remote.response.ProductDetailsResponse
import com.example.summitapp.data.remote.response.ProductResponse
import com.example.summitapp.data.remote.response.RegisterResponse
import com.example.summitapp.data.remote.response.SubcategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("User/register")
    fun registerUser(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("User/auth")
    fun loginUser(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("Category")
    fun getCategories(
    ): Call<CategoryResponse>

    @GET("product/search")
    fun getProducts(
        @Query("query") query: String
    ): Call<ProductResponse>

    @GET("product/details/{productId}")
    fun getProductDetails(
        @Path("productId") productId: Int
    ): Call<ProductDetailsResponse>

    @GET("subcategory")
    fun getSubcategories(@Query("category_id") categoryId: String): Call<SubcategoryResponse>



    companion object {
        fun getInstance(): ApiService {
            return ApiClient.retrofit.create(ApiService::class.java)
        }
    }
}

