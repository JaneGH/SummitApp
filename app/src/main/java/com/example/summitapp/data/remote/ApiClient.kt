
package com.example.summitapp.data.remote
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    val retrofit: Retrofit by lazy {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        // while sending request from emulator http://10.0.2.2/apolis/api/index.php or http://ip-address-of-laptop/apolis/api/index.php
        // while sending request from your device http://ip-address-of-laptop/apolis/api/index.php
        val gson = GsonBuilder()
            .registerTypeAdapter(Int::class.java, StringToIntAdapter())
            .registerTypeAdapter(Double::class.java, StringToDoubleAdapter())
            .setLenient()
            .create()

        Retrofit.Builder()
//            .baseUrl("http://10.0.0.104/myshop/index.php/")
            .baseUrl("http://10.0.2.2/myshop/index.php/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
}