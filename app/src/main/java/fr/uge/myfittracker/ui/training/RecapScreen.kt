package fr.uge.myfittracker.ui.training

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import fr.uge.myfittracker.data.model.SessionStat

@Composable
fun RecapScreen(sessionStat: String) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

        ) {
        Text(
            sessionStat
        )
    }
}