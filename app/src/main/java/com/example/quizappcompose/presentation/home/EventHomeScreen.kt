package com.example.quizappcompose.presentation.home

sealed class EventHomeScreen {

    data class SetNumberOfQuizzes(val numberOfQuizzes: Int): EventHomeScreen()
    data class SetCategory(val category: String): EventHomeScreen()
    data class SetDifficulty(val difficulty: String): EventHomeScreen()
    data class SetType(val type: String): EventHomeScreen()
}