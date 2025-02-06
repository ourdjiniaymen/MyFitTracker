package fr.uge.myfittracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.uge.myfittracker.ui.home.HomeScreen
import fr.uge.myfittracker.ui.home.viewmodel.HomeViewModel
import fr.uge.myfittracker.ui.home.viewmodel.StepCounterViewModel
import fr.uge.myfittracker.ui.settings.SettingsScreen
import fr.uge.myfittracker.ui.training.TrainingScreen

@Composable
fun NavGraph(navController: NavHostController) {

    //val context = LocalContext.current
    //val homeViewModel = remember { HomeViewModel(context) }
    val homeViewModel: HomeViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.navRoute
    ) {
        composable(BottomNavItem.Home.navRoute) {
            //HomeScreen(HomeViewModel())
            HomeScreen(homeViewModel)
        }
        composable(BottomNavItem.Train.navRoute) {
            TrainingScreen()
        }
        composable(BottomNavItem.Settings.navRoute) {
            SettingsScreen()
        }
    }
}

