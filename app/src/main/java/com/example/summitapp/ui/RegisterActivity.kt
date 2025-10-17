package com.example.summitapp.ui
import com.example.summitapp.showMessage
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.isVisible
import com.example.summitapp.Constants
import com.example.summitapp.data.remote.ApiService
import com.example.summitapp.data.remote.request.RegisterRequest
import com.example.summitapp.data.remote.response.RegisterResponse
import com.example.summitapp.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val apiService: ApiService = ApiService.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener{
            registerUser()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }

    }

    private fun registerUser() {
        val fullName = binding.etFullName.text.toString().trim()
        val mobile = binding.etMobile.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (fullName.isEmpty() || mobile.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        binding.btnSignUp.isEnabled = false

        val registerRequest = RegisterRequest(
            full_name = fullName,
            mobile_no = mobile,
            email_id = email,
            password = password
        )

        val call: Call<RegisterResponse> = apiService.registerUser(registerRequest)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                binding.progressBar.isVisible = false

                if (!response.isSuccessful) {
                    showMessage("Error", "Failed: ${response.code()} ${response.message()}")
                    return
                }

                val result = response.body()
                if (result != null) {
                    val pref = getSharedPreferences(Constants.SETTING, MODE_PRIVATE)
                    pref.edit{
                        putBoolean(Constants.LOGGED_IN, true)
                        putString(Constants.FULL_NAME, binding.etFullName.text.toString())
                    }
                    showMessage("Success", result.message)
                } else {
                    showMessage("Error", "Empty response from server")
                }
            }

            override fun onFailure(
                call: Call<RegisterResponse?>,
                t: Throwable
            ) {
                binding.progressBar.isVisible = false

                showMessage("Error", "Failed to register. Please retry again.")
            }
        })

        binding.progressBar.isVisible = true
    }
}