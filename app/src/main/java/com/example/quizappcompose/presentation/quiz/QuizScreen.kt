package com.example.quizappcompose.presentation.quiz


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizappcompose.presentation.common.QuizAppBar
import com.example.quizappcompose.presentation.util.Dimens
import com.example.quizappcompose.R
import com.example.quizappcompose.presentation.common.ButtonBox
import com.example.quizappcompose.presentation.quiz.component.QuizInterface
import com.example.quizappcompose.presentation.quiz.component.ShimmerEffectQuizInterface
import com.example.quizappcompose.presentation.util.Constants
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.quizappcompose.presentation.nav_graph.Routes
import kotlinx.coroutines.launch

@Preview
@Composable
fun PrevQuiz() {
    QuizScreen(
        numOfQuiz = 10,
        category = "General Knowledge",
        difficulty = "Easy",
        type = "Multiple Choice",
        event = {},
        state = StateQuizScreen()
        )
}

@Composable
fun QuizScreen(
    numOfQuiz: Int,
    category: String,
    difficulty: String,
    type: String,
    event: (EventQuizScreen) -> Unit,
    state: StateQuizScreen,
//    navController: NavController = rememberNavController()
) {
    LaunchedEffect(key1 = Unit) {
        val difficulty = when(difficulty) {
            "Medium" -> "medium"
            "Hard" -> "hard"
            else -> "easy"
        }

        val type = when(type) {
            "Multiple Choice" -> "multiple"
            else -> "boolean"
        }
        event(EventQuizScreen.GetQuizzes(numOfQuiz, Constants.categoriesMap[category]!!, difficulty, type))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        QuizAppBar(
            quizCategory = category,
            onBackClick = {
//             navController.navigate(route = Routes.HomeScreen.route)
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.SmallPadding)
        ) {
            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = " Questions : $numOfQuiz ",
                    color = colorResource(id = R.color.blue_grey),
                )
                Text(
                    text = "$difficulty ",
                    color = colorResource(id = R.color.blue_grey),
                )
            }

            Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.VerySmallViewHeight)
                    .clip(RoundedCornerShape(Dimens.MediumCornerRadius))
                    .background(colorResource(id = R.color.blue_grey))
            )

            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

            // quiz interface
            if(quizFetched(state)) {

                val pagerState = rememberPagerState() {
                    state.quizState.size
                }

                HorizontalPager(state = pagerState) {index ->
                    QuizInterface(
                        modifier = Modifier.weight(1f),
                        quizState = state.quizState[index],
                        onOptionSelected = { selectedIndex ->
                            event(EventQuizScreen.SetOptionSelected(index, selectedIndex))
                        },
                        qNumber = index + 1,
                    )
                }

                val buttonText by remember {
                    derivedStateOf {
                        when(pagerState.currentPage) {
                            0 -> {
                                listOf("", "Next")
                            }
                            state.quizState.size -1 -> {
                                listOf("Previous", "Submit")
                            }
                            else -> {
                                listOf("Previous", "Next")
                            }
                        }
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.MediumPadding)
                        .navigationBarsPadding()
                ) {
                    val scope = rememberCoroutineScope()
                    if(buttonText[0].isNotEmpty()){
                        ButtonBox(
                            text = "Previous",
                            padding = Dimens.SmallPadding,
                            fontSize = Dimens.SmallTextSize,
                            fraction = 0.43f
                        ) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    }
                    else {
                        ButtonBox(
                            text = "",
                            fontSize = Dimens.SmallTextSize,
                            fraction = 0.43f,
                            containerColor = colorResource(id = R.color.mid_night_blue),
                            borderColor = colorResource(id = R.color.mid_night_blue),
                        ) {
                        }
                    }


                    Spacer(modifier = Modifier.width(Dimens.SmallSpacerWidth))


                    ButtonBox(
                        text = buttonText[1],
                        padding = Dimens.SmallPadding,
                        fraction = 0.9f,
                        fontSize = Dimens.SmallTextSize,
                        borderColor = colorResource(R.color.orange),
                        containerColor = if(pagerState.currentPage == state.quizState.size - 1) colorResource(R.color.orange) else colorResource(R.color.dark_slate_blue),
                        textColor = colorResource(R.color.white)

                    ) {
                        if(pagerState.currentPage == state.quizState.size - 1) {
                            // TODO: Feature is not implemented
                            Log.d("score" , state.score.toString())

                        }
                        else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                }

            }



        }
    }
}

@Composable
fun quizFetched(state: StateQuizScreen): Boolean {

    return when {
        state.isLoading -> {
            ShimmerEffectQuizInterface()
            false
        }

        state.quizState.isNotEmpty() == true -> {
            true
        }

        else -> {
            Text(text = state.error.toString(), color = colorResource(id = R.color.white))
            false
        }
    }
}
