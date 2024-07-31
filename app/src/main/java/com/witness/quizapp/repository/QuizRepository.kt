package com.witness.quizapp.repository

import com.witness.quizapp.api.RetrofitInstance

class QuizRepository {
    suspend fun getQuizzes(amount: Int) = RetrofitInstance.api.getQuizzes(amount)
}
