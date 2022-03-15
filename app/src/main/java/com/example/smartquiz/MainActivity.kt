package com.example.smartquiz

import android.app.Activity
import android.content.ClipData.newIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders



private const val TAG = "MainActivity"
private const val KEY_QUESTION_INDEX = "com.example.smartquiz.index_question"
private const val KEY_TRUE_ANSWERS = "com.example.smartquiz.true_answers"
private const val KEY_CHEAT_COUNT = "com.example.smartquiz.cheat_count"
private const val CODE_REQUEST_FOR_CHEAT_ACTIVITY = 100


class MainActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnFinish: Button
    private lateinit var btnNewQuiz: Button
    private lateinit var btnShowAnswer: Button

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
        val currentCheatCount = savedInstanceState?.getInt(KEY_CHEAT_COUNT, 0) ?: 0
        questionViewModel.isCheatingCount = currentCheatCount
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
        btnShowAnswer = findViewById(R.id.btn_show_answer)
        btnPrevious.isVisible = false
        updateQuestion()

        btnTrue.setOnClickListener{
           checkAnswer(true)
        }
        btnFalse.setOnClickListener{
            checkAnswer(false)
        }

        btnNext.setOnClickListener{
            questionViewModel.nextQuestion()
            updateQuestion()
        }

        btnPrevious.setOnClickListener{
            questionViewModel.previousQuestion()
            updateQuestion()
        }

        questionText.setOnClickListener{
            questionViewModel.nextQuestion()
            updateQuestion()
        }

        btnFinish.setOnClickListener{
            showQuizResults()
            lockButtonsMove()
        }

        btnNewQuiz.setOnClickListener{
            startNewQuiz()
            updateQuestion()
            unlockButtonsMove()
        }

        btnShowAnswer.setOnClickListener{
            newIntentForCheatActivity()
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
        var answer_result = 0
        if (userAnswer == trueAnswerQuestion && questionViewModel.isAnswered == null ){
            questionViewModel.trueAnswerCount += 1
            questionViewModel.isCheckedAnswer(true)
            answer_result = R.string.true_answer
        } else {
            questionViewModel.isCheckedAnswer(false)
            answer_result = R.string.false_answer
        }
        if (questionViewModel.isCheating) {
            answer_result = R.string.reprimand_toast
        }
        Toast.makeText(this, answer_result, Toast.LENGTH_SHORT).show()
    }

    private fun showQuizResults() {
        var percentTrueAnswers: Float = (questionViewModel.trueAnswerCount / questionViewModel.bankQuestionSize.toFloat()) * 100
        if(questionViewModel.isCheatingCount > 0){
            Toast.makeText(this, "Вы - ЖУЛИК и подсмотрели ответ ${questionViewModel.isCheatingCount} раз(а)", Toast.LENGTH_LONG).show()
        }
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
        questionViewModel.isCheatingDelete()
        questionViewModel.isCheater = false
        questionViewModel.isCheatingCount = 0
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
        savedInstanceState.putInt(KEY_QUESTION_INDEX, questionViewModel.currentQuestion)
        savedInstanceState.putInt(KEY_TRUE_ANSWERS, questionViewModel.trueAnswerCount)
        savedInstanceState.putInt(KEY_CHEAT_COUNT, questionViewModel.isCheatingCount)
    }

    private fun newIntentForCheatActivity(){
        val currentQuestionTrueAnswer = questionViewModel.currentQuestionTrueAnswer
        val intent = CheatActivity.newIntent(this, currentQuestionTrueAnswer)
        startActivityForResult(intent, CODE_REQUEST_FOR_CHEAT_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == CODE_REQUEST_FOR_CHEAT_ACTIVITY) {
            questionViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            questionViewModel.isCheatingCount += 1
            questionViewModel.isCheating(questionViewModel.isCheater)
        }
    }
}



