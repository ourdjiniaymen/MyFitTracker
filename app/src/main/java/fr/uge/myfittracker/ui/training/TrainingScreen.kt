package fr.uge.myfittracker.ui.training

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import fr.uge.myfittracker.utils.Constants

@Composable
fun TrainingScreen(navController: NavHostController) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

        ) {
        Button(onClick = { navController.navigate(Constants.WORKOUT_SCREEN_ROUTE) }) {
            Text("Start")
        }
    }
}