package com.example.quizappcompose.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.quizappcompose.presentation.common.AppDropDownMenu
import com.example.quizappcompose.presentation.common.ButtonBox
import com.example.quizappcompose.presentation.util.Dimens
import com.example.quizappcompose.presentation.home.component.HomeHeader
import com.example.quizappcompose.presentation.nav_graph.Routes
import com.example.quizappcompose.presentation.util.Constants


@Composable
fun  HomeScreen(
    state: StateHomeScreen,
    event: (EventHomeScreen) -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        HomeHeader()

        Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))
        AppDropDownMenu(menuName = "Number of Questions", menuList = Constants.numberAsString, text = state.numberOfQuiz.toString() ,onDropDownClick = {event(EventHomeScreen.SetNumberOfQuizzes(it.toInt()))})

        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
        AppDropDownMenu(menuName = "Select Category", menuList = Constants.categories, text = state.category.toString(), onDropDownClick = {event(EventHomeScreen.SetCategory(it))})

        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
        AppDropDownMenu(menuName = "Select Difficulty", menuList = Constants.difficulty, text = state.difficulty.toString(), onDropDownClick = {event(EventHomeScreen.SetDifficulty(it))})

        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
        AppDropDownMenu(menuName = "Select Type", menuList = Constants.type, text = state.type.toString(), onDropDownClick = {event(EventHomeScreen.SetType(it))})

        Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))

        ButtonBox(text = "Generate Quiz", padding = Dimens.SmallPadding){
            navController.navigate(
                Routes.QuizScreen.passQuizParams(
                    numOfQuizzes = state.numberOfQuiz,
                    category = state.category.toString(),
                    difficulty = state.difficulty.toString(),
                    type = state.type.toString()
                )
            )
        }
    }
}