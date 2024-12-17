package iset.example.mindbalenceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.login_signuppi.SignupActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var usernameLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var registerPrompt: Button
    private lateinit var gifImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialisation des vues
        gifImageView = findViewById(R.id.logo)
        btnLogin = findViewById(R.id.loginButton)
        usernameLogin = findViewById(R.id.usernameField)
        passwordLogin = findViewById(R.id.passwordField)
        registerPrompt = findViewById(R.id.registerPrompt)

        // Charger un GIF dans l'ImageView avec Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.ani) // Assurez-vous que "ani" est un GIF dans votre dossier drawable
            .into(gifImageView)

        // Gestion du bouton Login
        btnLogin.setOnClickListener {
            handleLogin()
        }

        // Gestion du bouton Sign Up
        registerPrompt.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleLogin() {
        val username = usernameLogin.text.toString().trim()
        val password = passwordLogin.text.toString().trim()

        // Validation des champs
        when {
            username.isEmpty() -> {
                usernameLogin.error = "Username is required"
                usernameLogin.requestFocus()
            }
            password.isEmpty() -> {
                passwordLogin.error = "Password is required"
                passwordLogin.requestFocus()
            }
            else -> {
                loginUser(username, password)
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        val userService = RetrofitInstance.userApi

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = User(username = username, password = password)
                val response: Response<String> = userService.login(user)

                runOnUiThread {
                    if (response.isSuccessful) {
                        // Connexion réussie
                        Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()

                        // Rediriger vers l'activité principale
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("username",username.toString())
                        startActivity(intent)
                        finish()
                    } else {
                        // Gestion des erreurs
                        Toast.makeText(
                            this@LoginActivity,
                            "Error: ${response.errorBody()?.string()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@LoginActivity,
                        "An error occurred: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
