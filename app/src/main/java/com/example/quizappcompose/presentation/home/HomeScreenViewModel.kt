package com.example.quizappcompose.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeScreenViewModel : ViewModel() {

    private val _homeState = MutableStateFlow(StateHomeScreen())
    val homeState = _homeState

    fun onEvent(event: EventHomeScreen) {
        when(event) {
            is EventHomeScreen.SetNumberOfQuizzes -> {
                _homeState.value = homeState.value.copy(
                    numberOfQuiz = event.numberOfQuizzes
                )
            }

            is EventHomeScreen.SetCategory -> {
                _homeState.value = homeState.value.copy(
                    category = event.category
                )
            }
            is EventHomeScreen.SetDifficulty -> {
                _homeState.value = homeState.value.copy(
                    difficulty = event.difficulty
                )
            }
            is EventHomeScreen.SetType -> {
                _homeState.value = homeState.value.copy(
                    type = event.type
                )

            }
        }
    }
}