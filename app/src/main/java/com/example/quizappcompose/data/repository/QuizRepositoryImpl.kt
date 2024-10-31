package com.example.quizappcompose.data.repository

import com.example.quizappcompose.data.remote.QuizApi
import com.example.quizappcompose.domain.model.Quiz
import com.example.quizappcompose.domain.repository.QuizRepository

class QuizRepositoryImpl(
    private val quizApi: QuizApi
): QuizRepository {
    override suspend fun getQuizzes(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): List<Quiz> {
        return quizApi.getQuizzes(
            amount = amount,
            category = category,
            difficulty = difficulty,
            type = type
        ).results

    }


}