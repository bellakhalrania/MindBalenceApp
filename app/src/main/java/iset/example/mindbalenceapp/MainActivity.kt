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

        // Adjust view padding to account for system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frame_container_main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets // Return insets to propagate them
        }

        // Initialize with HomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container_main, HomeFragment())
                .commit()
        }

        // Set up BottomNavigationView with fragment change logic
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
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
