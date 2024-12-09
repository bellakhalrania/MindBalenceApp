package iset.example.mindbalenceapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import iset.example.mindbalenceapp.MainActivity
import iset.example.mindbalenceapp.databinding.ActivityWelcomeBinding


class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.welcomeBtn.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // This will close the WelcomeActivity so it doesn't appear in the back stack
        }

    }
}
