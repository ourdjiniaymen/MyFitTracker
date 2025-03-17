package fr.uge.myfittracker.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    var showStatistique by remember { mutableStateOf(false) }

    if (showStatistique) {
        StatistiqueScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Home Screen")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { showStatistique = true }) {
                Text(text = "Statistique")
            }
        }
    }
}
