package com.example.summitapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.summitapp.Constants
import com.example.summitapp.model.remote.ApiService
import com.example.summitapp.model.remote.request.LoginRequest
import com.example.summitapp.model.remote.response.LoginResponse
import com.example.summitapp.databinding.ActivityLoginBinding
import com.example.summitapp.showMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    val apiService: ApiService = ApiService.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvNoAccount.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener{
            loginUser()
        }

    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false

        val loginRequest = LoginRequest(
            emailId = email,
            password = password
        )

        val call:  Call<LoginResponse> = apiService.loginUser(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.isEnabled = true

                if (!response.isSuccessful) {
                    showMessage("Error", "Failed: ${response.code()} ${response.message()}")
                    return
                }

                val result = response.body()
                if (result != null) {
                    if (result.status == 0) {
                        val pref = getSharedPreferences(Constants.SETTING, MODE_PRIVATE)
                        pref.edit(commit = true) {
                            putBoolean(Constants.LOGGED_IN, true)
                            putString(Constants.FULL_NAME, response.body()?.user?.fullName ?: "")
                            putString(Constants.EMAIL_ID, response.body()?.user?.emailId ?: "")
                            putString(Constants.MOBILE_NO, response.body()?.user?.mobileNo ?: "")
                        }
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        showMessage("Error", result.message)
                    }
                } else {
                    showMessage("Error", "Empty response from server")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.isEnabled = true
                showMessage("Error", "Failed to login. Please retry.")
            }
        })
    }
}