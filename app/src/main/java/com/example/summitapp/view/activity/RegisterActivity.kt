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
import com.example.summitapp.viewmodel.LoginViewModel
import com.example.summitapp.model.data.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory }
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        binding.btnSignUp.setOnClickListener {
            registerViewModel.register(
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

        observeRegisterViewModel()
        observeLoginViewModel()
    }

    private fun observeRegisterViewModel() {
        registerViewModel.loading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.btnSignUp.isEnabled = !isLoading
        }

        registerViewModel.error.observe(this) { errorMsg ->
            errorMsg?.let { showMessage("Error", it) }
        }

        registerViewModel.registerResult.observe(this) { result ->
            result?.let {
                if (it.status == 0) {
                    val email = binding.etEmail.text.toString().trim()
                    val password = binding.etPassword.text.toString().trim()
                    loginViewModel.login(email, password)
                } else {
                    showMessage("Registration Failed", it.message)
                }
            }
        }
    }

    private fun observeLoginViewModel() {
        loginViewModel.loading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        loginViewModel.error.observe(this) { errorMsg ->
            errorMsg?.let { showMessage("Error", it) }
        }

        loginViewModel.loginResult.observe(this) { result ->
            result?.let {
                if (it.status == 0 && it.user != null) {
                    saveUserToPreferences(it.user)
                    goToMainScreen()
                } else {
                    showMessage("Login Failed", it.message)
                }
            }
        }
    }

    private fun saveUserToPreferences(user: User) {
        val pref = getSharedPreferences(Constants.SETTING, MODE_PRIVATE)
        pref.edit(commit = true) {
            putBoolean(Constants.LOGGED_IN, true)
            putString(Constants.FULL_NAME, user.fullName)
            putString(Constants.EMAIL_ID, user.emailId)
            putString(Constants.MOBILE_NO, user.mobileNo)
            putString(Constants.USER_ID, user.userId)
        }
    }

    private fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
