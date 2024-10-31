package com.example.quizappcompose.data.remote.dto

import com.example.quizappcompose.domain.model.Quiz

data class QuizResponse(
    val response_code: Int,
    val results: List<Quiz>
)