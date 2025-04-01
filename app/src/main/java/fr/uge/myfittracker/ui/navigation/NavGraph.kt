package fr.uge.myfittracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.uge.myfittracker.ui.creation.ExerciseScreen
import fr.uge.myfittracker.ui.creation.PlanWithSessionScreen
import fr.uge.myfittracker.ui.creation.SessionWithSeriesScreen
import fr.uge.myfittracker.ui.games.EndlessRunnerGame
import fr.uge.myfittracker.ui.games.MemoryGame
import fr.uge.myfittracker.ui.games.ReactionTimeGame
import fr.uge.myfittracker.ui.home.HomeScreen
import fr.uge.myfittracker.ui.home.viewmodel.HomeViewModel
import fr.uge.myfittracker.ui.home.viewmodel.StepCounterViewModel
import fr.uge.myfittracker.ui.settings.SettingsScreen
import fr.uge.myfittracker.ui.training.HistoryScreen
import fr.uge.myfittracker.ui.training.SeriesWithExerciseViewModel
import fr.uge.myfittracker.ui.training.TrainingPlan
import fr.uge.myfittracker.ui.training.TrainingPlanListScreen
import fr.uge.myfittracker.ui.training.TrainingScreen
import fr.uge.myfittracker.ui.training.WorkoutScreen
import fr.uge.myfittracker.ui.training.viewmodel.TrainingPlanViewModel
import fr.uge.myfittracker.utils.Constants

@Composable
fun NavGraph(navController: NavHostController) {
    val seriesWithExerciseViewModel: SeriesWithExerciseViewModel = viewModel(factory = SeriesWithExerciseViewModel.Factory)

    val trainingViewModel: TrainingPlanViewModel = viewModel(factory = TrainingPlanViewModel.Factory)

    //val context = LocalContext.current
    //val homeViewModel = remember { HomeViewModel(context) }
    val homeViewModel: HomeViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.navRoute
    ) {
        composable(BottomNavItem.Home.navRoute) {
            //HomeScreen(HomeViewModel())
            HomeScreen(homeViewModel,navController)
        }
        composable(BottomNavItem.Train.navRoute) {
            TrainingScreen(navController)
        }
        composable(BottomNavItem.Settings.navRoute) {
            SettingsScreen()
        }
        composable("sessionScreen") {
            SessionWithSeriesScreen(navController, seriesWithExerciseViewModel)
        }
        composable("planScreen") {
            PlanWithSessionScreen(navController, seriesWithExerciseViewModel, trainingViewModel)
        }
        composable("exerciseScreen") {
            ExerciseScreen(navController, seriesWithExerciseViewModel)
        }
        composable("PlanEntrainements") {
            TrainingPlanListScreen(navController, trainingViewModel)
        }
        composable("Plan") {
                TrainingPlan(navController, trainingViewModel)
        }
        composable("History") {
            HistoryScreen(navController, trainingViewModel)
        }
        composable("session") {
        }

        composable(Constants.WORKOUT_SCREEN_ROUTE) {
            WorkoutScreen(navController, trainingViewModel )
        }
        composable("memoryGame") {
            MemoryGame(navController)
        }
        composable("reactionTime") {
            ReactionTimeGame(navController)
        }
        composable("runnerGame") {
            EndlessRunnerGame(navController)
        }
    }
}

