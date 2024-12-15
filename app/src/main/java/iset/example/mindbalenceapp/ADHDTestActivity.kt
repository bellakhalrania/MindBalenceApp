package iset.example.mindbalenceapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ADHDTestActivity : AppCompatActivity() {

    private var questions = listOf(
        "Do you often have trouble paying attention?",
        "Are you easily distracted?",
        "Do you have trouble staying organized?",
        "Do you have trouble following through on instructions?",
        "Do you fidget or squirm a lot?",
        "Do you have trouble sitting still?",
        "Do you have trouble waiting your turn?",
        "Do you often interrupt others?",
        "Do you have trouble controlling your impulses?",
        "Do you often feel restless or have trouble relaxing?"
    )

    private var answers = listOf("Never", "Rarely", "Sometimes", "Often", "Always")
    private var currentQuestionIndex = 0
    private var score = 0
    private var selectedAnswerIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adhdtest)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupQuiz()
    }
    private fun setupQuiz() {
        val questionTextView = findViewById<TextView>(R.id.questionTextView)
        val answerButtons = listOf(
            findViewById<Button>(R.id.answerButton1),
            findViewById<Button>(R.id.answerButton2),
            findViewById<Button>(R.id.answerButton3),
            findViewById<Button>(R.id.answerButton4),
            findViewById<Button>(R.id.answerButton5)
        )
        val nextButton = findViewById<Button>(R.id.nextButton)


        displayQuestion(questionTextView, answerButtons)


        answerButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                selectedAnswerIndex = index + 1
                answerButtons.forEach { it.isEnabled = true }
                button.isEnabled = false
            }
        }


        nextButton.setOnClickListener {
            if (selectedAnswerIndex == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            } else {
                score += selectedAnswerIndex
                selectedAnswerIndex = -1
                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    displayQuestion(questionTextView, answerButtons)
                } else {
                    showResult()
                }
            }
        }
    }

    private fun displayQuestion(questionTextView: TextView, answerButtons: List<Button>) {
        questionTextView.text = questions[currentQuestionIndex]
        answerButtons.forEachIndexed { index, button ->
            button.text = answers[index]
            button.isEnabled = true
        }
    }

    private fun showResult() {
        setContentView(R.layout.activity_adhd_result)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val restartButton = findViewById<Button>(R.id.restartButton)


        val resultMessage = when {
            score > 7 -> "Your results suggest symptoms consistent with ADHD. It's important to consult a healthcare professional for a comprehensive evaluation and support."
            score in 4..7 -> "Your results indicate some symptoms that might suggest attentional difficulties. Consider monitoring these and seeking professional advice if needed."
            else -> "Your results are within a typical range. Continue practicing habits that support focus and attention, and consult a professional if challenges arise."
        }

        resultTextView.text = "Your score: $score\n\n$resultMessage"


        restartButton.setOnClickListener {
            resetQuiz()
        }
    }


    private fun resetQuiz() {
        score = 0
        currentQuestionIndex = 0
        selectedAnswerIndex = -1
        setContentView(R.layout.activity_adhdtest)
        setupQuiz()
    }
}