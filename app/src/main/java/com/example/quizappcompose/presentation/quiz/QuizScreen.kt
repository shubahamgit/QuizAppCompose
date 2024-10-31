package com.example.quizappcompose.presentation.quiz

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizappcompose.presentation.common.QuizAppBar
import com.example.quizappcompose.presentation.util.Dimens
import com.example.quizappcompose.R
import com.example.quizappcompose.presentation.quiz.component.QuizInterface
import com.example.quizappcompose.presentation.util.Constants

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
    state: StateQuizScreen
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
        QuizAppBar(quizCategory = category, onBackClick = {})

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

            QuizInterface(
                modifier = Modifier.weight(1f),
                onOptionSelected = {},
                qNumber = 1
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.MediumPadding)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {  },
                    modifier = Modifier
                        .height(Dimens.MediumBoxHeight)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.blue_grey)
                    ),
                    shape = RoundedCornerShape(Dimens.LargeCornerRadius),
                    border = BorderStroke(Dimens.SmallBorderWidth, colorResource(id = R.color.blue_grey))
                ) {
                    Text(
                        text = "Previous",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = colorResource(id = R.color.black)
                    )
                }

                Spacer(modifier = Modifier.width(Dimens.SmallSpacerWidth))

                Button(
                    onClick = {  },
                    modifier = Modifier
                        .height(Dimens.MediumBoxHeight)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.blue_grey)
                    ),
                    shape = RoundedCornerShape(Dimens.LargeCornerRadius),
                    border = BorderStroke(Dimens.SmallBorderWidth, colorResource(id = R.color.blue_grey))
                ) {
                    Text(
                        text = "Next",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = colorResource(id = R.color.black)
                    )
                }

            }
        }
    }
}