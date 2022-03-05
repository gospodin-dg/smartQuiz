package com.example.smartquiz

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*

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
        } else {
            currentQuestion = 0
        }
    }

    private fun previousQuestion(){
        if (currentQuestion > 0){
            currentQuestion = currentQuestion - 1
        } else {
            currentQuestion = bankQuestion.size - 1
        }
    }

    private fun checkAnswer(userAnswer: Boolean ){
        val trueAnswerQuestion = bankQuestion[currentQuestion].trueAnswer
        if (userAnswer == trueAnswerQuestion){
            Toast.makeText(this, R.string.true_answer, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.false_answer, Toast.LENGTH_SHORT).show()
        }
    }

}



