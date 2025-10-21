package com.example.summitapp.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.isVisible
import com.example.summitapp.Constants
import com.example.summitapp.databinding.ActivityRegisterBinding
import com.example.summitapp.showMessage
import com.example.summitapp.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            viewModel.register(
                fullName = binding.etFullName.text.toString().trim(),
                mobile = binding.etMobile.text.toString().trim(),
                email = binding.etEmail.text.toString().trim(),
                password = binding.etPassword.text.toString().trim(),
                confirmPassword = binding.etConfirmPassword.text.toString().trim()
            )
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.btnSignUp.isEnabled = !isLoading
        }

        viewModel.error.observe(this) { errorMsg ->
            errorMsg?.let { showMessage("Error", it) }
        }

        viewModel.registerResult.observe(this) { result ->
            result?.let {
                val pref = getSharedPreferences(Constants.SETTING, MODE_PRIVATE)
                pref.edit(commit = true) {
                    putBoolean(Constants.LOGGED_IN, true)
                    putString(Constants.FULL_NAME, binding.etFullName.text.toString())
                    putString(Constants.EMAIL_ID, binding.etEmail.text.toString())
                    putString(Constants.MOBILE_NO, binding.etMobile.text.toString())
                }

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}
