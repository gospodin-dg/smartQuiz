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
    private var currentQuestion: Int = 0
    private val bankQuestion = listOf<Question>(
        Question(R.string.question_0, true),
        Question(R.string.question_1, true),
        Question(R.string.question_2, true),
        Question(R.string.question_3, false),
        Question(R.string.question_4, true),
        Question(R.string.question_5, true),
        Question(R.string.question_6, true)
    )
    private var trueAnswerCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        init()
        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val questionViewModel: QuestionViewModel = provider.get(QuestionViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel: $questionViewModel")
    }

    fun init() {
        questionText = findViewById(R.id.question_text)
        btnTrue = findViewById(R.id.btn_true)
        btnFalse = findViewById(R.id.btn_false)
        btnNext = findViewById(R.id.btn_next)
        btnPrevious = findViewById(R.id.btn_previous)
        btnPrevious.isVisible = false
        questionText.setText(bankQuestion[currentQuestion].questionText)

        btnTrue.setOnClickListener{view: View ->
           checkAnswer(true)
        }
        btnFalse.setOnClickListener{view: View ->
            checkAnswer(false)
        }

        btnNext.setOnClickListener{view: View ->
            updateQuestion(view)
        }

        btnPrevious.setOnClickListener{view: View ->
            updateQuestion(view)
        }

        questionText.setOnClickListener{view: View ->
            updateQuestion(view)
        }
    }

    private fun updateQuestion(view: View) {
        val view_id = view.id
        when(view_id){
            R.id.question_text -> nextQuestion()
            R.id.btn_next -> nextQuestion()
            R.id.btn_previous -> previousQuestion()
        }
        //currentQuestion = (currentQuestion + 1) % bankQuestion.size
        questionText.setText(bankQuestion[currentQuestion].questionText)
    }

    private fun nextQuestion(){
        if (currentQuestion < (bankQuestion.size-1)){
            currentQuestion = currentQuestion + 1
            btnPrevious.isVisible = true
        }
        if(currentQuestion == (bankQuestion.size-1)){
            btnNext.isVisible = false
        }
        unlockButtons()
    }

    private fun previousQuestion(){
        if (currentQuestion > 0){
            currentQuestion = currentQuestion - 1
            btnNext.isVisible = true
        }
        if(currentQuestion == 0){
            btnPrevious.isVisible = false
        }
        unlockButtons()
    }

    private fun checkAnswer(userAnswer: Boolean ){
        val trueAnswerQuestion = bankQuestion[currentQuestion].trueAnswer
        if (userAnswer == trueAnswerQuestion){
            trueAnswerCount += 1
            Toast.makeText(this, R.string.true_answer, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.false_answer, Toast.LENGTH_SHORT).show()
        }
        lockButtons()
        if (currentQuestion == (bankQuestion.size-1)){
            var percentTrueAnswers: Float = trueAnswerCount / bankQuestion.size.toFloat() *100
            Toast.makeText(this, "Тест закончен. Процент правильных ответов - $percentTrueAnswers%", Toast.LENGTH_LONG).show()
            trueAnswerCount = 0
        }
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

    fun lockButtons() {
        btnTrue.isEnabled = false
        btnFalse.isEnabled = false
    }

    fun unlockButtons(){
        btnTrue.isEnabled = true
        btnFalse.isEnabled = true
    }

}



