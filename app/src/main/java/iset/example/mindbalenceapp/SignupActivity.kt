package iset.example.mindbalenceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import iset.example.mindbalenceapp.LoginActivity
import iset.example.mindbalenceapp.MainActivity
import iset.example.mindbalenceapp.R
import iset.example.mindbalenceapp.RetrofitInstance
import iset.example.mindbalenceapp.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var usernameField: EditText
    private lateinit var fullNameField: EditText
    private lateinit var signupEmailField: EditText
    private lateinit var signupPasswordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var signupButton: Button
    private lateinit var alreadyAccountPrompt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Apply window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        usernameField = findViewById(R.id.usernameField)
        fullNameField = findViewById(R.id.fullNameField)
        signupEmailField = findViewById(R.id.signupEmailField)
        signupPasswordField = findViewById(R.id.signupPasswordField)
        confirmPasswordField = findViewById(R.id.confirmPasswordField)
        signupButton = findViewById(R.id.signupButton)
        alreadyAccountPrompt = findViewById(R.id.btnlogin)

        // Set click listeners
        signupButton.setOnClickListener {
            validateFields()
        }

        alreadyAccountPrompt.setOnClickListener {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateFields() {
        val username = usernameField.text.toString().trim()
        val fullName = fullNameField.text.toString().trim()
        val email = signupEmailField.text.toString().trim()
        val password = signupPasswordField.text.toString()
        val confirmPassword = confirmPasswordField.text.toString()

        // Validate fields
        when {
            username.isEmpty() -> {
                usernameField.error = "Username is required"
                usernameField.requestFocus()
            }
            fullName.isEmpty() -> {
                fullNameField.error = "Full Name is required"
                fullNameField.requestFocus()
            }
            email.isEmpty() -> {
                signupEmailField.error = "Email is required"
                signupEmailField.requestFocus()
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                signupEmailField.error = "Invalid email format"
                signupEmailField.requestFocus()
            }
            password.isEmpty() -> {
                signupPasswordField.error = "Password is required"
                signupPasswordField.requestFocus()
            }
            password.length < 6 -> {
                signupPasswordField.error = "Password must be at least 6 characters"
                signupPasswordField.requestFocus()
            }
            confirmPassword.isEmpty() -> {
                confirmPasswordField.error = "Please confirm your password"
                confirmPasswordField.requestFocus()
            }
            password != confirmPassword -> {
                confirmPasswordField.error = "Passwords do not match"
                confirmPasswordField.requestFocus()
            }
            else -> {
                // All validations passed
                registerUser(username, fullName, email, password)
            }
        }
    }

    private fun registerUser(username: String, fullName: String, email: String, password: String) {
        val userService = RetrofitInstance.userApi

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = User(username, fullName, email, password)
                val response = userService.register(user)

                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@SignupActivity,
                            "Registration Successful!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navigate to login screen after successful registration
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@SignupActivity,
                            "Error: ${response.errorBody()?.string()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@SignupActivity,
                        "An error occurred: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
