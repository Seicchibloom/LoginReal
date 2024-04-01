package com.pack.real

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.pack.real.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.google.android.material.textfield.TextInputLayout
import com.pack.real.NextActivity

@AndroidEntryPoint
class SignInFragment : AppCompatActivity() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    private var isFormValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.viewModel = viewModel

        binding.signInButton.setOnClickListener {
            if (isFormValid) {
                // Proceed to sign in
                navigateToNextPage()
            } else {
                Toast.makeText(
                    this@SignInFragment,
                    "Form fields are invalid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Set up field validation as text changes
        binding.emailField.doOnTextChanged { text, _, _, _ ->
            validateFields()
        }
        binding.passwordField.doOnTextChanged { text, _, _, _ ->
            validateFields()
        }
    }
    private fun navigateToNextPage() {
        val intent = Intent(this, NextActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun validateFields() {
        val email = binding.emailField.text.toString()
        val password = binding.passwordField.text.toString()

        val isEmailValid = email.isNotEmpty()
        val isPasswordFilled = password.isNotEmpty()
        val isPasswordValidLength = password.length >= 8

        val errorText = "Please fill out this field."
        val passwordLengthError = "Password must be 8 or more characters"

        binding.emailContainer.setFieldError(!isEmailValid, errorText)
        binding.passwordContainer.setFieldError(!isPasswordFilled, errorText)

        if (isPasswordFilled) {
            binding.passwordContainer.setFieldError(!isPasswordValidLength, passwordLengthError)
        }

        // Update the form validity
        isFormValid = isEmailValid && isPasswordFilled && isPasswordValidLength
    }

    private fun TextInputLayout.setFieldError(isError: Boolean, errorMessage: String) {
        error = if (isError) errorMessage else null
    }
}
