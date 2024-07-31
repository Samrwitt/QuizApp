package com.witness.quizapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.witness.quizapp.viewmodel.QuizViewModel
import com.witness.quizapp.utils.UiState

@Composable
fun QuizScreen(viewModel: QuizViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var answer by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var correctAnswers by remember { mutableStateOf(0) }
    var questionCount by remember { mutableStateOf(10) } // Default number of questions
    var isQuizStarted by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (!isQuizStarted) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Quiz App!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(32.dp))

                BasicTextField(
                    value = questionCount.toString(),
                    onValueChange = { questionCount = it.toIntOrNull() ?: 10 },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.surface)
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = {
                    isQuizStarted = true
                    viewModel.fetchQuizzes(questionCount)
                }) {
                    Text(text = "Start Quiz")
                }
            }
        } else {
            when (uiState) {
                is UiState.LOADING -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.SUCCESS -> {
                    val quizzes = (uiState as UiState.SUCCESS).data.results
                    if (currentQuestionIndex < quizzes.size) {
                        val currentQuestion = quizzes[currentQuestionIndex]
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Question ${currentQuestionIndex + 1}: ${currentQuestion.question}",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(32.dp))

                            BasicTextField(
                                value = answer,
                                onValueChange = {
                                    answer = it
                                    showError = false
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
                                    .padding(16.dp),
                                textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.surface)
                            )

                            if (showError) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Please enter your answer",
                                    color = MaterialTheme.colorScheme.error,
                                    style = TextStyle(fontSize = 14.sp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (answer.isNotBlank()) {
                                        if (answer.equals(currentQuestion.correctAnswer, ignoreCase = true)) {
                                            correctAnswers++
                                        }
                                        currentQuestionIndex++
                                        answer = ""
                                        showError = false
                                    } else {
                                        showError = true
                                    }
                                }
                            ) {
                                Text(text = "Next Question")
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "You have completed the quiz!\nCorrect answers: $correctAnswers out of ${quizzes.size}")
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = {
                                isQuizStarted = false
                                currentQuestionIndex = 0
                                correctAnswers = 0
                                answer = ""
                                showError = false
                            },
                                modifier = Modifier.padding(top = 128.dp)
                            ) {
                                Text(text = "Try Again")
                            }
                        }
                    }
                }
                is UiState.ERROR -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Failed to load quizzes")
                    }
                }
            }
        }
    }
}
