package fr.uge.myfittracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.ui.navigation.BottomNavBar
import fr.uge.myfittracker.ui.navigation.NavGraph
import fr.uge.myfittracker.ui.theme.MyFitTrackerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val database by lazy { AppDatabase.getDatabase(this) }
        val planDao by lazy { database.planDao() }

        super.onCreate(savedInstanceState)
        setContent {
            MyFitTrackerTheme(darkTheme = false) {
                MainScreen()
            }
        }
        /*lifecycleScope.launch(Dispatchers.IO) {
            val plan = Plan(id = 1, name = "Test Plan")
            planDao.insertPlan(plan) // Insert a test plan
            val allPlans = planDao.getAllPlans() // Fetch all plans

            Log.d("DatabaseTest", "Retrieved Plans: $allPlans")
        }*/

    }
}


@Composable
fun MainScreen() {

    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController, darkTheme = false) }
    ) { innerPadding ->
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(innerPadding)) {

            NavGraph(
                navController = navController,
            )
        }
    }
}

