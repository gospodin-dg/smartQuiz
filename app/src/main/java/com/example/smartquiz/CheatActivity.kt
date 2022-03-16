package com.example.smartquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible

private const val ANSWER_IS_TRUE = "com.example.smartquiz.true_answer"
const val EXTRA_ANSWER_SHOWN = "com.example.smartquiz.answer_shown"
private const val KEY_CHEATING_STATE = "com.example.smartquiz.is_cheating_state"

class CheatActivity : AppCompatActivity() {

    private var trueAnswer: Boolean = false
    private lateinit var btnShowAnswer: Button
    private lateinit var txtViewShowAnswer: TextView
    private var isCheatingState: Boolean = false
    private lateinit var txtViewApiLevel: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        init(savedInstanceState)
    }

    @SuppressLint("RestrictedApi")
    fun init(savedInstanceState: Bundle?) {
        isCheatingState = savedInstanceState?.getBoolean(KEY_CHEATING_STATE, false) ?: false
        trueAnswer = intent.getBooleanExtra(ANSWER_IS_TRUE, false)
        btnShowAnswer = findViewById(R.id.btn_show_answer)
        txtViewShowAnswer = findViewById(R.id.text_answer)
        txtViewApiLevel = findViewById(R.id.api_level_device)
        val apiLevelDevice: String = Build.VERSION.SDK_INT.toString()
        txtViewApiLevel.setText("API Level $apiLevelDevice")
        if (isCheatingState) {
            showAnswer()
        }
        btnShowAnswer.setOnClickListener{
            showAnswer()
        }

    }

    private fun isCheating(cheat: Boolean){
        val intent = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, cheat)
        }
        setResult(Activity.RESULT_OK, intent)
    }

    private fun showAnswer(){
        val answer = when(trueAnswer) {
            true -> R.string.answer_yes
            else -> R.string.answer_no
        }
        txtViewShowAnswer.setText(answer)
        txtViewShowAnswer.isVisible = true
        isCheatingState = true
        isCheating(true)
    }

    companion object{
        fun newIntent(context: Context, trueAnswer: Boolean): Intent {
            return Intent(context, CheatActivity::class.java).apply{
                putExtra(ANSWER_IS_TRUE, trueAnswer)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_CHEATING_STATE, isCheatingState)
    }
}