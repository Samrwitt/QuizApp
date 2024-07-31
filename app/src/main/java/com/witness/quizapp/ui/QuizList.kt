package com.witness.quizapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.witness.quizapp.model.Quiz

@Composable
fun QuizList(quizzes: List<Quiz>) {
    Column {
        quizzes.forEach { quiz ->
            QuizItem(title = quiz.question, description = quiz.correctAnswer)
        }
    }
}
