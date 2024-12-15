package iset.example.mindbalenceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.widget.Toast



class DepressionTestActivity : AppCompatActivity() {

    private var questions = listOf(
        "I have lost interest in activities I used to enjoy.",
        "I have difficulty concentrating or making decisions.",
        "I have feelings of worthlessness or guilt.",
        "I have thoughts of death or suicide.",
        "I have changes in my appetite.",
        "I have changes in my sleep patterns.",
        "I have decreased energy levels.",
        "I have difficulty controlling my emotions.",
        "I have physical aches and pains.",
        "I have withdrawn from social activities."
    )

    private var answers = listOf("Never", "Rarely", "Sometimes", "Often", "Always")
    private var currentQuestionIndex = 0
    private var score = 0
    private var selectedAnswerIndex = -1






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depression_test)

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
                button.isEnabled = false // Highlight selected option
            }
        }

        // Handle the "Next" button
        nextButton.setOnClickListener {
            if (selectedAnswerIndex == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            } else {
                score += selectedAnswerIndex // Increment score based on selected answer
                selectedAnswerIndex = -1 // Reset selection
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
        setContentView(R.layout.activity_depression_result)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val restartButton = findViewById<Button>(R.id.restartButton)


        val resultMessage = when {
            score > 7 -> "Your results suggest symptoms of depression. It's important to consult a healthcare professional for a proper diagnosis and support."
            score in 4..7 -> "Your results indicate mild symptoms that might benefit from attention. Monitor your well-being and consider seeking advice if needed."
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
        setContentView(R.layout.activity_depression_test)
        setupQuiz()
    }
}
