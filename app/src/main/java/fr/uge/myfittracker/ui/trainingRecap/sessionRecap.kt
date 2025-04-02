package fr.uge.myfittracker.ui.trainingRecap


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.uge.myfittracker.R
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesStat
import fr.uge.myfittracker.data.model.SeriesWithExercise
import fr.uge.myfittracker.data.model.Session
import fr.uge.myfittracker.data.model.SessionStat
import fr.uge.myfittracker.data.model.SessionType
import fr.uge.myfittracker.ui.navigation.BottomNavItem
import fr.uge.myfittracker.ui.theme.darkGrey
import fr.uge.myfittracker.ui.theme.primary
import fr.uge.myfittracker.ui.theme.secondary
import fr.uge.myfittracker.ui.training.viewmodel.TrainingPlanViewModel

val sessionStat = SessionStat(
    session = Session(
        type = SessionType.FULL_BODY,
        repetition = 1
    ),
    seriesStat = listOf(
        SeriesStat(
            series = SeriesWithExercise(
                exercise = Exercise(
                    name = "Warming up",
                    description = "Preparation description"
                ),
                series = Series(duration = 10, repetition = null)
            ),
            achievedScore = 10,
            isCompleted = true
        ),
        SeriesStat(
            series = SeriesWithExercise(
                exercise = Exercise(name = "Pushups", description = "pushups description"),
                series = Series(duration = null, repetition = 15)
            ),
            achievedScore = 10,
            isCompleted = false
        ),
        SeriesStat(
            series = SeriesWithExercise(
                exercise = Exercise(name = "Pullups", description = "pullups description"),
                series = Series(duration = null, repetition = 15)
            ),
            achievedScore = 15,
            isCompleted = true
        )
    )

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionRecapScreen(navController: NavController, viewModel: TrainingPlanViewModel) {
    val currentSessionRecap = viewModel.currentSessionStat.collectAsState().value
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Recap",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth() // Occupe toute la largeur
                )
            },
            navigationIcon = {
                // Bouton à gauche
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(4.dp), // Ajustez le padding si nécessaire
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "back arrow",
                        tint = Color.Black,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }
            },
            /*actions = {
                // Bouton à droite
                Button(
                    onClick = {
                       },
                    modifier = Modifier.padding(4.dp), // Ajustez le padding si nécessaire
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = "Enregistrer",
                        style = TextStyle(fontSize = 16.sp),
                        color = secondary
                    )
                }
            },*/
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White, // Couleur de fond de l'AppBar
                titleContentColor = Color.Black, // Couleur du titre
                actionIconContentColor = Color.White, // Couleur des icônes d'action
                navigationIconContentColor = Color.White // Couleur de l'icône de navigation
            )
        )
        Text(
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            text = "Session ${currentSessionRecap?.session?.type}",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.Black
        )


        LazyColumn() {
            items(currentSessionRecap?.seriesStat!!) { seriesStat ->
                SerieStatItem(seriesStat)
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,){
            Button(
                onClick = {
                },
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color.White
                )
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_restart), // Icône flèche vers le bas
                        contentDescription = "Menu déroulant",
                        modifier = Modifier.size(24.dp),
                        tint = darkGrey
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recommencer", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                },
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_skip), // Icône flèche vers le bas
                    contentDescription = "Menu déroulant",
                    modifier = Modifier.size(24.dp),
                    tint = darkGrey
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Fermer", color = Color.White)
            }
        }

    }
}
