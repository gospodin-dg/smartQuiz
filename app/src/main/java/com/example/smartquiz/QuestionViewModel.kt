package com.example.smartquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG: String = "QuestionViewModel"

class QuestionViewModel: ViewModel() {

    init {
        Log.d(TAG, "QuestionViewModel is create")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "QuestionViewModel was cleared")
    }

}