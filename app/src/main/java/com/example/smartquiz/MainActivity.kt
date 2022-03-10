package com.example.smartquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: ImageButton
    private lateinit var btnPrevious: ImageButton
    private var trueAnswerCount: Int = 0

    private val questionViewModel: QuestionViewModel by lazy {
        ViewModelProviders.of(this).get(QuestionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        questionText = findViewById(R.id.question_text)
        btnTrue = findViewById(R.id.btn_true)
        btnFalse = findViewById(R.id.btn_false)
        btnNext = findViewById(R.id.btn_next)
        btnPrevious = findViewById(R.id.btn_previous)
        btnPrevious.isVisible = false
        updateQuestion()

        btnTrue.setOnClickListener{view: View ->
           checkAnswer(true)
        }
        btnFalse.setOnClickListener{view: View ->
            checkAnswer(false)
        }

        btnNext.setOnClickListener{view: View ->
            questionViewModel.nextQuestion()
            updateQuestion()
        }

        btnPrevious.setOnClickListener{view: View ->
            questionViewModel.previousQuestion()
            updateQuestion()
        }

        questionText.setOnClickListener{view: View ->
            questionViewModel.nextQuestion()
            updateQuestion()
        }
    }

    private fun updateQuestion() {
        unlockButtons()
        val currentQuestionText = questionViewModel.currentQuestionText
        questionText.setText(currentQuestionText)
        btnNext.isVisible = questionViewModel.currentQuestion < (questionViewModel.bankQuestionSize-1)
        btnPrevious.isVisible = questionViewModel.currentQuestion > 0
    }

    private fun checkAnswer(userAnswer: Boolean ){
        lockButtons()
        val trueAnswerQuestion = questionViewModel.currentQuestionTrueAnswer



        if (userAnswer == trueAnswerQuestion){
            trueAnswerCount += 1
            Toast.makeText(this, R.string.true_answer, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.false_answer, Toast.LENGTH_SHORT).show()
        }
        if (questionViewModel.currentQuestion == (questionViewModel.bankQuestionSize-1)){
            showQuizResults()
        }
    }

    private fun showQuizResults() {
        var percentTrueAnswers: Float = (trueAnswerCount / questionViewModel.bankQuestionSize.toFloat()) * 100
        Toast.makeText(this, "Тест закончен. Процент правильных ответов - $percentTrueAnswers%", Toast.LENGTH_LONG).show()
        trueAnswerCount = 0
        questionViewModel.currentQuestion = 0
    }

    private fun lockButtons() {
        btnTrue.isEnabled = false
        btnFalse.isEnabled = false
    }

    private fun unlockButtons(){
        btnTrue.isEnabled = true
        btnFalse.isEnabled = true
    }








    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

}



