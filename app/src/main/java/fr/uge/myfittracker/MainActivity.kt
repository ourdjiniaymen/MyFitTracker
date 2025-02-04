package fr.uge.myfittracker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vérifier et demander la permission pour le capteur de pas
        requestActivityRecognitionPermission()

        setContent {
            MyFitTrackerTheme(darkTheme = false) {
                MainScreen()
            }
        }
    }

    // Vérifie si la permission est accordée, sinon la demande
    private fun requestActivityRecognitionPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Vérifier si API >= 29 (Android 10+)
            if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                // Demander la permission
                permissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
            }
        }
    }

    // Gestion de la réponse de l'utilisateur après la demande de permission
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Permission accordée ✅", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission refusée ❌", Toast.LENGTH_SHORT).show()
        }
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

