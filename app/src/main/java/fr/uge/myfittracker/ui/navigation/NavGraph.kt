package fr.uge.myfittracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.uge.myfittracker.data.model.SessionStat
import fr.uge.myfittracker.ui.home.HomeScreen
import fr.uge.myfittracker.ui.settings.SettingsScreen
import fr.uge.myfittracker.ui.training.RecapScreen
import fr.uge.myfittracker.ui.training.TrainingScreen
import fr.uge.myfittracker.ui.training.WorkoutScreen
import fr.uge.myfittracker.utils.Constants

@Composable
fun NavGraph(navController: NavHostController) {
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

        composable(Constants.WORKOUT_SCREEN_ROUTE) {
            WorkoutScreen(navController)
        }
        composable("${Constants.RECAP_SCREEN}/{sessionRecap}") {backStackEntry->
            //val sessionStat = backStackEntry
              //  .arguments?.getParcelable<SessionStat>(Constants.SESSION_STAT)
            val sessionStat = backStackEntry.arguments?.getString(Constants.SESSION_STAT)
            RecapScreen(sessionStat?:"Empty")
        }

    }
}

