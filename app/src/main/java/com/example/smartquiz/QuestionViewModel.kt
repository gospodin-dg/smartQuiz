package com.example.smartquiz

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel

class QuestionViewModel: ViewModel() {

    private val bankQuestion = listOf(
        Question(R.string.question_0, true, null),
        Question(R.string.question_1, true, null),
        Question(R.string.question_2, true, null),
        Question(R.string.question_3, false, null),
        Question(R.string.question_4, true, null),
        Question(R.string.question_5, true, null),
        Question(R.string.question_6, true, null)
    )

    var currentQuestion: Int = 0
    var trueAnswerCount: Int = 0
    var isCheater: Boolean = false
    var isCheatingCount: Int = 0

    val currentQuestionTrueAnswer: Boolean
        get() = bankQuestion[currentQuestion].trueAnswer

    val currentQuestionText: Int
        get() = bankQuestion[currentQuestion].questionText

    val bankQuestionSize: Int
        get() = bankQuestion.size

    val isAnswered: Boolean?
        get() = bankQuestion[currentQuestion].isAnswered

    val isCheating: Boolean
        get() = bankQuestion[currentQuestion].isCheating

    fun nextQuestion(){
        if (currentQuestion < (bankQuestion.size-1)){
            currentQuestion += 1
        }
    }

    fun previousQuestion(){
        if (currentQuestion > 0){
            currentQuestion = currentQuestion - 1
        }
    }

    fun isCheckedAnswer(flag: Boolean){
        bankQuestion[currentQuestion].isAnswered = flag
    }

    fun isCheating(flag: Boolean){
        bankQuestion[currentQuestion].isCheating = flag
    }

    fun isAnsweredNullable(){
        for (question in bankQuestion) {
            question.isAnswered = null
        }
    }

    fun isCheatingDelete(){
        for (question in bankQuestion) {
            question.isCheating = false
        }
    }

}