package fr.uge.myfittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import fr.uge.myfittracker.ui.navigation.BottomNavBar
import fr.uge.myfittracker.ui.navigation.NavGraph
import fr.uge.myfittracker.ui.theme.MyFitTrackerTheme
import fr.uge.myfittracker.ui.theme.light

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFitTrackerTheme(darkTheme = false) {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.background(color = light),
        bottomBar = { BottomNavBar(navController, darkTheme = false) }
    ) { innerPadding ->
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(innerPadding)) {

            NavGraph(
                navController = navController,
            )
        }
    }
}

