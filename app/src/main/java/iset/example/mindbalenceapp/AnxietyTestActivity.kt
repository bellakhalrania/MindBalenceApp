package iset.example.mindbalenceapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AnxietyTestActivity : AppCompatActivity(){

    private var questions = listOf(
        "How often have you felt restless or fidgety?",
        "How often have you been unable to concentrate or your mind has wandered?",
        "How often have you been bothered by trouble falling or staying asleep?",
        "How often have you been bothered by feeling tired or having low energy?",
        "How often have you been bothered by feeling worthless or guilty?",
        "How often have you been bothered by trouble making decisions?",
        "How often have you been bothered by muscle tension?",
        "How often have you been bothered by being easily startled?",
        "How often have you been bothered by feeling afraid that something terrible might happen?",
        "How often have you been bothered by having a racing heart?"
    )

    private var answers = listOf("Never", "Rarely", "Sometimes", "Often", "Always")
    private var currentQuestionIndex = 0
    private var score = 0
    private var selectedAnswerIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anxiety_test)
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
        setContentView(R.layout.activity_anxiety_result)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val restartButton = findViewById<Button>(R.id.restartButton)


        val resultMessage = when {
            score > 30 -> "Your results suggest symptoms of anxiety. It's important to consult a healthcare professional for a proper diagnosis and support."
            score in 20..30 -> "Your results indicate mild symptoms of anxiety that might benefit from attention. Monitor your well-being and consider seeking advice if needed."
            else -> "Your results are within a normal range. Maintain self-care, and seek help if you notice changes in your mental health."
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
        setContentView(R.layout.activity_anxiety_test)
        setupQuiz()
    }
}