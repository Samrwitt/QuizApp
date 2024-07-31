package com.witness.quizapp.api

import com.witness.quizapp.model.QuizResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api.php")
    suspend fun getQuizzes(
        @Query("amount") amount: Int
    ): QuizResponse
}
