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
private const val KEY_QUESTION_INDEX = "index_question"
private const val KEY_TRUE_ANSWERS = "true_answers"

class MainActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnFinish: Button
    private lateinit var btnNewQuiz: Button

    private lateinit var btnNext: ImageButton
    private lateinit var btnPrevious: ImageButton


    private val questionViewModel: QuestionViewModel by lazy {
        ViewModelProviders.of(this).get(QuestionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate(Bundle?) called")
        val currentQuestion = savedInstanceState?.getInt(KEY_QUESTION_INDEX, 0) ?: 0
        questionViewModel.currentQuestion = currentQuestion
        val currentTrueAnswers = savedInstanceState?.getInt(KEY_TRUE_ANSWERS, 0) ?: 0
        questionViewModel.trueAnswerCount = currentTrueAnswers
        init()
    }

    fun init() {
        questionText = findViewById(R.id.question_text)
        btnTrue = findViewById(R.id.btn_true)
        btnFalse = findViewById(R.id.btn_false)
        btnNext = findViewById(R.id.btn_next)
        btnPrevious = findViewById(R.id.btn_previous)
        btnFinish = findViewById(R.id.btn_finish)
        btnNewQuiz = findViewById(R.id.btn_new_quiz)
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

        btnFinish.setOnClickListener{view: View ->
            showQuizResults()
            lockButtonsMove()
        }

        btnNewQuiz.setOnClickListener{view: View ->
            startNewQuiz()
            updateQuestion()
            unlockButtonsMove()
        }

    }

    private fun updateQuestion() {
        checkingDoubleAnswer()
        btnNext.isVisible = questionViewModel.currentQuestion < (questionViewModel.bankQuestionSize-1)
        btnPrevious.isVisible = questionViewModel.currentQuestion > 0
        val currentQuestionText = questionViewModel.currentQuestionText
        questionText.setText(currentQuestionText)

    }

    private fun checkAnswer(userAnswer: Boolean ){
        lockButtonsAnswer()
        val trueAnswerQuestion = questionViewModel.currentQuestionTrueAnswer
        if (userAnswer == trueAnswerQuestion && questionViewModel.isAnswered == null ){
            questionViewModel.trueAnswerCount += 1
            questionViewModel.isCheckedAnswer(true)
            Toast.makeText(this, R.string.true_answer, Toast.LENGTH_SHORT).show()
        } else {
            questionViewModel.isCheckedAnswer(false)
            Toast.makeText(this, R.string.false_answer, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showQuizResults() {
        var percentTrueAnswers: Float = (questionViewModel.trueAnswerCount / questionViewModel.bankQuestionSize.toFloat()) * 100
        Toast.makeText(this, "Тест закончен. Процент правильных ответов - $percentTrueAnswers%", Toast.LENGTH_LONG).show()
        questionViewModel.trueAnswerCount = 0
    }

    private fun lockButtonsAnswer() {
        btnTrue.isEnabled = false
        btnFalse.isEnabled = false
    }

    private fun unlockButtonsAnswer(){
        btnTrue.isEnabled = true
        btnFalse.isEnabled = true
    }

    private fun checkingDoubleAnswer(){
        if(questionViewModel.isAnswered != null) {
            lockButtonsAnswer()
        } else {
            unlockButtonsAnswer()
        }
    }

    private fun startNewQuiz(){
        questionViewModel.currentQuestion = 0
        questionViewModel.isAnsweredNullable()
    }

    private fun lockButtonsMove(){
        btnNext.isVisible = false
        btnPrevious.isVisible = false
        questionText.isEnabled = false
        btnFinish.isEnabled = false
    }

    private fun unlockButtonsMove(){
        questionText.isEnabled = true
        btnFinish.isEnabled = true
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.d(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_QUESTION_INDEX, questionViewModel.currentQuestion)
        savedInstanceState.putInt(KEY_TRUE_ANSWERS, questionViewModel.trueAnswerCount)
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



