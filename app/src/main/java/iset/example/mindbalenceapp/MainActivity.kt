package iset.example.mindbalenceapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import iset.example.mindbalenceapp.fragments.HomeFragment
import iset.example.mindbalenceapp.fragments.MeditationFragment
import iset.example.mindbalenceapp.fragments.MyProfileFragment

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frame_container_main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container_main, HomeFragment())
                .commit()
        }


        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container_main, HomeFragment())
                        .commit()
                    true
                }
                R.id.test-> {
                    val intent = Intent(this, TestActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container_main, MyProfileFragment())
                        .commit()
                    true
                }
                R.id.chatbot -> {
                    val intent = Intent(this, chatActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
