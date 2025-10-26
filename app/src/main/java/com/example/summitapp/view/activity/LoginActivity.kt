package com.example.summitapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.summitapp.Constants
import com.example.summitapp.databinding.ActivityLoginBinding
import com.example.summitapp.showMessage
import com.example.summitapp.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvNoAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.login(email, password)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnLogin.isEnabled = !isLoading
        }

        viewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                showMessage("Error", it)
            }
        }

        viewModel.loginResult.observe(this) { result ->
            result?.let {
                if (it.status == 0) {
                    val pref = getSharedPreferences(Constants.SETTING, MODE_PRIVATE)
                    pref.edit(commit = true) {
                        putBoolean(Constants.LOGGED_IN, true)
                        putString(Constants.USER_ID, it.user?.userId ?: "")
                        putString(Constants.FULL_NAME, it.user?.fullName ?: "")
                        putString(Constants.EMAIL_ID, it.user?.emailId ?: "")
                        putString(Constants.MOBILE_NO, it.user?.mobileNo ?: "")
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    showMessage("Error", it.message)
                }
            }
        }
    }
}
