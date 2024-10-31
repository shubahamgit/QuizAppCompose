package com.example.quizappcompose.presentation.quiz.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizappcompose.R
import com.example.quizappcompose.presentation.common.QuizOption
import com.example.quizappcompose.presentation.util.Dimens

@Preview
@Composable
fun QuizInterfacePreview() {
    QuizInterface(
        onOptionSelected = {},
        qNumber = 1,
    )
}


@Composable
fun QuizInterface(
    onOptionSelected: (Int) -> Unit,
    qNumber: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$qNumber .",
                    modifier = Modifier.weight(1f),
                    color = colorResource(id = R.color.blue_grey),
                    fontSize = Dimens.SmallTextSize
                )
                Text(
                    modifier = Modifier.weight(9f),
                    text = "Which cartoon do you see ?",
                    color = colorResource(id = R.color.blue_grey),
                    fontSize = Dimens.SmallTextSize
                )
            }

            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

            Column (
                modifier = Modifier.padding(horizontal = 15.dp),
            ) {

                val options = listOf(
                    "A" to "Doramon",
                    "B" to "Oggy",
                    "C" to "Ben 10",
                    "D" to "Tom and Jerry",
                )

                Column {
                    options.forEachIndexed { index, (optionNumber, optionText) ->
                        if (optionText.isNotEmpty()) {
                            QuizOption(
                                optionNumber = optionNumber,
                                options = optionText,
                                selected = false,
                                onOptionClick = { onOptionSelected(index) },
                                onUnselectOption = { onOptionSelected(-1) }
                            )

                        }
                        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
                    }
                }
            }
            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))
        }
    }
}