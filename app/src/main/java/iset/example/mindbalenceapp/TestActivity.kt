package iset.example.mindbalenceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test)

        val adhdTestButton: Button = findViewById(R.id.basic_yoga_btn)
        adhdTestButton.setOnClickListener {
            val intent = Intent(this, ADHDTestActivity::class.java)
            startActivity(intent)
        }

        val anxietyTestButton: Button = findViewById(R.id.full_body_yoga_btn)
        anxietyTestButton.setOnClickListener {
            val intent = Intent(this, AnxietyTestActivity::class.java)
            startActivity(intent)
        }

        val deppressionTestButton: Button = findViewById(R.id.depression)
        deppressionTestButton.setOnClickListener {
            val intent = Intent(this, DepressionTestActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adhdReadMore: TextView = findViewById(R.id.adhd_read_more)
        adhdReadMore.setOnClickListener {
            val intent = Intent(this, ADHDActivity::class.java)
            startActivity(intent)
        }


        val anxietyReadMore: TextView = findViewById(R.id.anexity_read_more)
        anxietyReadMore.setOnClickListener {
            val intent = Intent(this, AnxietyActivity::class.java)
            startActivity(intent)
        }


        val depressionReadMore: TextView = findViewById(R.id.depression_read_more)
        depressionReadMore.setOnClickListener {
            val intent = Intent(this, DepressionActivity::class.java)
            startActivity(intent)
        }
    }
}



