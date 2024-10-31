package com.example.quizappcompose.presentation.quiz

import com.example.quizappcompose.domain.model.Quiz

data class StateQuizScreen(
    val isLoading: Boolean = false,
    val data: List<Quiz> ? = listOf(),
    val error: String ? = ""
)