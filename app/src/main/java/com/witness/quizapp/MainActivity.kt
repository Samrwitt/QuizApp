package com.witness.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.witness.quizapp.repository.QuizRepository
import com.witness.quizapp.ui.QuizScreen
import com.witness.quizapp.ui.HomeScreen
import com.witness.quizapp.ui.theme.QuizAppTheme
import com.witness.quizapp.viewmodel.QuizViewModel
import com.witness.quizapp.viewmodel.QuizViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = QuizRepository() // Initialize your repository
        val viewModelFactory = QuizViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(QuizViewModel::class.java)

        setContent {
            QuizAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome_screen") {
                    composable("welcome_screen") { HomeScreen(navController) }
                    composable("quiz_screen") { QuizScreen(viewModel) }
                }
            }
        }
    }
}
