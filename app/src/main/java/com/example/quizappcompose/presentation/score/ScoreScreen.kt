package com.example.quizappcompose.presentation.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quizappcompose.R
import com.example.quizappcompose.presentation.util.Dimens
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import java.text.DecimalFormat

@Preview
@Composable
fun ScoreScreenPreview() {
    ScoreScreen(
        numOfQuestions = 16,
        numOfCorrectAnswers = 3
    )
}

@Composable
fun ScoreScreen(
    numOfQuestions: Int,
    numOfCorrectAnswers: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.MediumPadding),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    // TODO: go to home screen
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close_24px),
                    contentDescription = "close",
                    tint = colorResource(id = R.color.blue_grey),
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .clip(shape = RoundedCornerShape(Dimens.MediumCornerRadius))
                .background(colorResource(id = R.color.blue_grey)),
            contentAlignment = Alignment.Center
        )
        {
            Column(
                modifier = Modifier.padding(
                    horizontal = Dimens.MediumPadding,
                    vertical = Dimens.MediumPadding
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.congrats))
                val annotatedString = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("You attempted ")
                    }
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("$numOfQuestions questions")
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(" and from that ")
                    }
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.green))) {
                        append("$numOfCorrectAnswers answers")
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(" were correct")
                    }
                }
                val scorePercentage = calculatePercentage(numOfQuestions, numOfCorrectAnswers)

                LottieAnimation(
                    modifier = Modifier.size(Dimens.LargeLottieAnimSize),
                    composition = composition,
                    iterations = 100,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )


                Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))

                Text(
                    text = "Congrats!",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = Dimens.MediumTextSize,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))

                Text(
                    text = "$scorePercentage% Score",
                    color = colorResource(id = R.color.green),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = Dimens.LargeTextSize,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))

                Text(
                    text = "Quiz completed successfully.",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = Dimens.SmallTextSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))

                Text(
                    text = annotatedString,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = Dimens.SmallTextSize,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Share with us : ",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = Dimens.SmallTextSize,
                    )

                    Spacer(modifier = Modifier.width(Dimens.SmallSpacerWidth))

                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.instagram),
                        contentDescription = "instagram",
                    )

                    Spacer(modifier = Modifier.width(Dimens.SmallSpacerWidth))

                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "instagram",
                    )

                    Spacer(modifier = Modifier.width(Dimens.SmallSpacerWidth))

                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.twitter),
                        contentDescription = "instagram",
                    )
                }

            }
        }
    }
}

fun calculatePercentage(k: Int, n: Int): Double {
    require(k >= 0 && n < k) { "Invalid input: k must be non-negative and n must be positive" }
    val percentage = (n.toDouble() / k.toDouble()) * 100.0
    return DecimalFormat("#.##").format(percentage).toDouble()
}