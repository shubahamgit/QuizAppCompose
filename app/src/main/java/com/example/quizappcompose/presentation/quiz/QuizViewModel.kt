package com.example.quizappcompose.presentation.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizappcompose.common.Resource
import com.example.quizappcompose.domain.model.Quiz
import com.example.quizappcompose.domain.usecases.GetQuizzesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.text.replace

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
            is EventQuizScreen.SetOptionSelected -> {
                updateQuizStateList(
                    event.quizStateIndex,
                    event.selectedOption
                )
            }

            else -> {}
        }
    }

    private fun updateQuizStateList(quizStateIndex: Int, selectedOption: Int) {
        val updateQuizStateList = mutableListOf<QuizState>()
         quizList.value.quizState.forEachIndexed { index, quizState ->
             updateQuizStateList.add(
                 if (quizStateIndex == index) {
                     quizState.copy(selectedOptions = selectedOption)
                 } else {
                     quizState
                 }
             )
         }

        _quizList.value = quizList.value.copy(quizState = updateQuizStateList)

        updateScore(_quizList.value.quizState[quizStateIndex])

    }

    private fun updateScore(quizState: QuizState) {
        if(quizState.selectedOptions != -1) {
            val correctAnswer = quizState.quiz?.correct_answer
            val selectedAnswer = quizState.selectedOptions?.let {
                quizState.shuffledOptions[it].replace("&quot;", "\"").replace("&#039;", "\'")
            }

            Log.d("check", "$correctAnswer --> $selectedAnswer")

            if(correctAnswer == selectedAnswer) {
                val prevScore = _quizList.value.score
                _quizList.value = _quizList.value.copy(score = prevScore + 1)
            }
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
                        val listOfQuizState : List<QuizState> = getListOfQuizState(resource.data!!)
                        _quizList.value = StateQuizScreen(
                            quizState = listOfQuizState
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


fun getListOfQuizState(data: List<Quiz>): List<QuizState> {
    val listOfQuizState = mutableListOf<QuizState>()

    for(quiz in data) {
        val shuffledQuestions = mutableListOf<String>().apply {
            add(quiz.correct_answer)
            addAll(quiz.incorrect_answers)
            shuffle()
        }

        listOfQuizState.add(QuizState(quiz, shuffledQuestions , -1))

    }

    return listOfQuizState
}