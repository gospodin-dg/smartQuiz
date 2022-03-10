package com.example.smartquiz

import androidx.annotation.StringRes

data class Question (@StringRes val questionText: Int, val trueAnswer: Boolean, var isAnswered: Boolean?)
