package com.example.quizappcompose.presentation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quizappcompose.presentation.home.HomeScreen
import com.example.quizappcompose.presentation.home.HomeScreenViewModel
import com.example.quizappcompose.presentation.quiz.QuizScreen
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.quizappcompose.presentation.quiz.QuizViewModel

@Composable
fun SetNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen.route) {

        composable(route = Routes.HomeScreen.route) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val state by  viewModel.homeState.collectAsState()
            HomeScreen(
                state = state,
                event = viewModel::onEvent,
                navController = navController
            )
        }

        composable(
            route = Routes.QuizScreen.route,
            arguments = listOf(
                navArgument(ARG_KEY_QUIZ_NUMBER){ type = NavType.IntType },
                navArgument(ARG_KEY_QUIZ_CATEGORY){ type = NavType.StringType },
                navArgument(ARG_KEY_QUIZ_DIFFICULTY) { type = NavType.StringType },
                navArgument(ARG_KEY_QUIZ_TYPE){ type = NavType.StringType }
            )
        ){ backStackEntry ->
            val numOfQuizzes = backStackEntry.arguments?.getInt(ARG_KEY_QUIZ_NUMBER) ?: 0
            val category = backStackEntry.arguments?.getString(ARG_KEY_QUIZ_CATEGORY) ?: ""
            val difficulty = backStackEntry.arguments?.getString(ARG_KEY_QUIZ_DIFFICULTY) ?: ""
            val type = backStackEntry.arguments?.getString(ARG_KEY_QUIZ_TYPE) ?: ""

            val viewModel: QuizViewModel = hiltViewModel()
            val state by viewModel.quizList.collectAsState()
            QuizScreen(
                numOfQuiz = numOfQuizzes,
                category = category,
                difficulty = difficulty,
                type = type,
                event = viewModel::onEvent,
                state = state,
            )
        }

    }
}