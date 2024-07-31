package com.witness.quizapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(navController: NavController) {
    var playerName by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Quiz App!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(32.dp))

            BasicTextField(
                value = playerName,
                onValueChange = { newValue ->
                    playerName = newValue
                    showError = false // Clear error when playerName
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.surface)
            )

            if (showError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please enter your name",
                    color = MaterialTheme.colorScheme.surface,
                    style = TextStyle(fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (playerName.isNotBlank()) {
                        navController.navigate("quiz_screen")
                    } else {
                        showError = true
                    }
                },
                enabled = playerName.isNotBlank()
            ) {
                Text(text = "Start Quiz")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}
