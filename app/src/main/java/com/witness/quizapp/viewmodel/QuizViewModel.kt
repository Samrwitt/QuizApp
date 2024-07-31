package com.witness.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witness.quizapp.model.QuizResponse
import com.witness.quizapp.repository.QuizRepository
import com.witness.quizapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<QuizResponse>>(UiState.LOADING)
    val uiState: StateFlow<UiState<QuizResponse>> = _uiState

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    init {
        fetchQuizzes(50)
    }

    fun fetchQuizzes(amount: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getQuizzes(amount)
                _uiState.value = UiState.SUCCESS(response)
            } catch (e: Exception) {
                _uiState.value = UiState.ERROR(e)
            }
        }
    }

    fun moveToNextQuestion() {
        _currentQuestionIndex.value += 1
    }
}
