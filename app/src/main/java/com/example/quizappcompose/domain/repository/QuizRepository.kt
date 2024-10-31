package com.example.quizappcompose.domain.repository

import com.example.quizappcompose.domain.model.Quiz

interface QuizRepository {

    suspend fun getQuizzes(
        amount:Int,
        category:Int,
        difficulty:String,
        type: String
    ): List<Quiz>
}