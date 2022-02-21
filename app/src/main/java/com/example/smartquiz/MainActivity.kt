package com.example.smartquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private var currentQuestion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        createQuestions()

    }

    fun init() {
        questionText = findViewById(R.id.question_text)
        btnTrue = findViewById(R.id.btn_true)
        btnFalse = findViewById(R.id.btn_false)
        btnTrue.setOnClickListener{view: View ->
            Toast.makeText(this, R.string.true_answer, Toast.LENGTH_SHORT).also {
                it.setGravity(Gravity.TOP, 0,0)
                it.show()
            }
        }
        btnFalse.setOnClickListener{view: View ->
            Toast.makeText(this, R.string.false_answer, Toast.LENGTH_SHORT).also {
                it.setGravity(Gravity.TOP, 0,0)
                it.show()
            }
        }
    }

    fun createQuestions() {

    }

}


