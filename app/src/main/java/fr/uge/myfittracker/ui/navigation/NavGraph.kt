package fr.uge.myfittracker.ui.navigation

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.uge.myfittracker.ui.creation.ExerciseScreen
import fr.uge.myfittracker.ui.creation.SessionWithSeriesScreen
import fr.uge.myfittracker.ui.home.HomeScreen
import fr.uge.myfittracker.ui.settings.SettingsScreen
import fr.uge.myfittracker.ui.training.SeriesWithExerciseViewModel
import fr.uge.myfittracker.ui.training.TrainingScreen

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: SeriesWithExerciseViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.navRoute
    ) {
        composable(BottomNavItem.Home.navRoute) {
            HomeScreen()
        }
        composable(BottomNavItem.Train.navRoute) {
            SessionWithSeriesScreen(navController, viewModel)
        }
        composable(BottomNavItem.Settings.navRoute) {
            SettingsScreen()
        }
        composable("exerciseScreen") {
            ExerciseScreen(navController, viewModel)
        }
    }
}

