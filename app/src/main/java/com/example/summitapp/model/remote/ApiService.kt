package com.example.summitapp.model.remote
import com.example.summitapp.model.remote.request.AddressRequest
import com.example.summitapp.model.remote.request.LoginRequest
import com.example.summitapp.model.remote.request.RegisterRequest
import com.example.summitapp.model.remote.response.AddressResponse
import com.example.summitapp.model.remote.response.BasicResponse
import com.example.summitapp.model.remote.response.CategoryResponse
import com.example.summitapp.model.remote.response.LoginResponse
import com.example.summitapp.model.remote.response.ProductDetailsResponse
import com.example.summitapp.model.remote.response.ProductResponse
import com.example.summitapp.model.remote.response.RegisterResponse
import com.example.summitapp.model.remote.response.SubcategoryResponse
import retrofit2.Call
import retrofit2.Response
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

    @Headers("Content-Type: application/json")
    @POST("User/address")
    suspend fun addUserAddress(
        @Body request: AddressRequest
    ): Response<BasicResponse>


    @GET("User/addresses/{user_id}")
    suspend fun getUserAddresses(
        @Path("user_id") userId: Int
    ): Response<AddressResponse>

    companion object {
        fun getInstance(): ApiService {
            return ApiClient.retrofit.create(ApiService::class.java)
        }
    }
}

