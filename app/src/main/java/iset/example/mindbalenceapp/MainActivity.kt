package iset.example.mindbalenceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import iset.example.mindbalenceapp.fragments.HomeFragment
import iset.example.mindbalenceapp.fragments.MeditationFragment
import iset.example.mindbalenceapp.fragments.MyProfileFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gestion des insets pour adapter la vue aux barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frame_container_main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialisation avec le fragment HomeFragment
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_container_main, HomeFragment())
            .commit()

        // Configuration de la navigation avec BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container_main, HomeFragment())
                        .commit()
                    true
                }
                R.id.meditation -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container_main, MeditationFragment())
                        .commit()
                    true
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container_main, MyProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
