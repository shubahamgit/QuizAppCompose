package com.example.quizappcompose.presentation.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationConstants.DefaultDurationMillis
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.quizappcompose.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizappcompose.presentation.util.Dimens
import kotlinx.coroutines.selects.select

@Preview
@Composable
fun QuizOptionPreview() {
    QuizOption(
        optionNumber = "A",
        options = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        selected = false,
        onOptionClick = {},
        onUnselectOption = {}
    )
}


@Composable
fun QuizOption(
    optionNumber: String,
    options: String,
    selected: Boolean,
    onOptionClick: () -> Unit,
    onUnselectOption: () -> Unit
) {
    val optionTextColor = if(selected) colorResource(id = R.color.blue_grey) else colorResource(id = R.color.black)
    val startColor by animateColorAsState(
        targetValue = if(selected) colorResource(id = R.color.orange) else colorResource(id = R.color.blue_grey),
        animationSpec = tween(durationMillis = DefaultDurationMillis, easing = LinearEasing),
        label = "selected"
    )

    Box(
        modifier = Modifier
            .noRippleClickable(onOptionClick)
            .fillMaxWidth()
            .height(Dimens.MediumBoxHeight)
            .clip(RoundedCornerShape(Dimens.LargeCornerRadius))
            .background(
                color = startColor,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(!selected) {
                Box(
                    modifier = Modifier
                        .size(Dimens.SmallCircleShape)
                        .weight(1.5f)
                        .shadow(10.dp, CircleShape,true, colorResource(id = R.color.black))
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.orange)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = optionNumber,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.blue_grey),
                        textAlign = TextAlign.Center,
                    )
                }
            }

            else {
                Box(
                    modifier = Modifier
                        .alpha(1.5f)
                )
            }

            Spacer(modifier = Modifier
                .width(Dimens.VerySmallSpacerWidth)
                .weight(0.6f)
            )

            Text(
                modifier = Modifier.weight(7.1f),
                text = options,
                color = optionTextColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                fontSize = 16.sp
            )

            if(selected) {
                Box(
                    modifier = Modifier
                        .size(Dimens.SmallCircleShape)
                        .weight(1.5f)
                        .shadow(10.dp, CircleShape,true, colorResource(id = R.color.black))
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.blue_grey)),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { onUnselectOption() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.close_24px),
                            contentDescription = "close",
                            tint = colorResource(id = R.color.orange)
                        )
                    }

                }
            }

            else {
                Box(
                    modifier = Modifier
                        .alpha(1.5f)
                )
            }


        }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}