package com.example.quizappcompose.presentation.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizappcompose.common.Resource
import com.example.quizappcompose.domain.usecases.GetQuizzesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizzesUseCases: GetQuizzesUseCases
): ViewModel() {

    private val _quizList = MutableStateFlow(StateQuizScreen())
    val quizList = _quizList


    fun onEvent(event: EventQuizScreen) {
        when(event) {
            is EventQuizScreen.GetQuizzes -> {
                getQuizzes(
                    amount = event.numOfQuizzes,
                    category = event.category,
                    difficulty = event.difficulty,
                    type = event.type
                )
            }

            else -> {}
        }
    }


    private fun getQuizzes(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ) {
        viewModelScope.launch{
            getQuizzesUseCases(
                amount = amount,
                category = category,
                difficulty = difficulty,
                type = type
            ).collect{ resource ->
                when(resource) {
                    is Resource.Loading -> {
                        Log.d("quiz", "Loading")
                        _quizList.value = StateQuizScreen(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        Log.d("quiz", "Success")
                        for(quiz in resource.data!!) {
                            Log.d("quiz", quiz.toString())
                        }
                        _quizList.value = StateQuizScreen(
                            data = resource.data
                        )
                    }

                    is Resource.Error -> {
                        Log.d("quiz", resource.message.toString())
                        _quizList.value = StateQuizScreen(
                            error = resource.message.toString()
                        )

                    }

                    else -> {}
                }

            }

        }


    }


}