package fr.uge.myfittracker.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.uge.myfittracker.ui.home.HomeScreen
import fr.uge.myfittracker.ui.settings.SettingsScreen
import fr.uge.myfittracker.ui.training.HistoryScreen
import fr.uge.myfittracker.ui.training.Session
import fr.uge.myfittracker.ui.training.TrainingPlan
import fr.uge.myfittracker.ui.training.TrainingPlanListScreen
import fr.uge.myfittracker.ui.training.TrainingScreen
import fr.uge.myfittracker.ui.training.viewmodel.TrainingPlanViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val trainingViewModel: TrainingPlanViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.navRoute
    ) {
        composable(BottomNavItem.Home.navRoute) {
            HomeScreen()
        }
        composable(BottomNavItem.Train.navRoute) {
            TrainingScreen(navController)
        }
        composable(BottomNavItem.Settings.navRoute) {
            SettingsScreen()
        }
        composable("PlanEntrainements") {
            TrainingPlanListScreen(navController, trainingViewModel)
        }
        composable("Plan") {
            val planId = trainingViewModel.currentPlan.collectAsState(initial = null).value?.id
            if (planId != null) {
                Log.d("planid", planId.toString())
                TrainingPlan(navController, planId.toLong(), trainingViewModel)
            }
        }
        composable("History") {
            HistoryScreen(navController, trainingViewModel)
        }
        composable("session") {
              Log.d("session", trainingViewModel.currentSession.collectAsState().toString())
        }
    }
}

