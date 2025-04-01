package fr.uge.myfittracker.ui.creation

import Step
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import fr.uge.myfittracker.data.model.SessionType
import fr.uge.myfittracker.data.model.SessionWithSeries
import fr.uge.myfittracker.ui.theme.black
import fr.uge.myfittracker.ui.theme.colorPalette
import fr.uge.myfittracker.ui.theme.darkGrey
import fr.uge.myfittracker.ui.theme.darkerGrey
import fr.uge.myfittracker.ui.theme.primary
import fr.uge.myfittracker.ui.theme.secondary
import fr.uge.myfittracker.ui.training.SeriesWithExerciseViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionWithSeriesScreen(navController: NavController, viewModel: SeriesWithExerciseViewModel) {
    var isExpanded by remember { mutableStateOf(false) }
    val currentSession by viewModel.currentSession.collectAsState()
    var selectedSessionType by remember {
        mutableStateOf(currentSession?.type ?: SessionType.FULL_BODY)
    }
    var defaultRepitition = if (currentSession != null){
        currentSession?.repetition.toString()
    }else{
        ""
    }
    var repititionController by remember { mutableStateOf(TextFieldValue(defaultRepitition)) }
    val series by viewModel.exerciseSeries.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Nouvelle session",
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
                    Text(
                        text = "Annuler",
                        style = TextStyle(fontSize = 16.sp),
                        color = secondary
                    )
                }
            },
            actions = {
                // Bouton à droite
                Button(
                    onClick = {
                        if (series.isNotEmpty()){
                            viewModel.setCurrentSession(selectedSessionType, 1)
                            viewModel.addSessionWithSeries()
                            navController.popBackStack()
                        }
                        else{
                            Toast.makeText(context, "Vous devriez ajouter au moins une serie",Toast.LENGTH_LONG ).show()}
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
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White, // Couleur de fond de l'AppBar
                titleContentColor = Color.Black, // Couleur du titre
                actionIconContentColor = Color.White, // Couleur des icônes d'action
                navigationIconContentColor = Color.White // Couleur de l'icône de navigation
            )
        )
        Text(
            style = TextStyle(fontSize = 20.sp),
            text = "Session",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.Black
        )
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
        ) {
            // Champ de texte personnalisé pour le menu déroulant
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .menuAnchor() // Permet d'ouvrir le menu déroulant
                    .padding(8.dp), // Espacement interne
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Texte affichant la sélection actuelle
                Text(
                    text = selectedSessionType.name,
                    modifier = Modifier.weight(1f),
                    color = if (selectedSessionType == SessionType.FULL_BODY) darkerGrey else Color.Black
                )
                // Icône personnalisée
                Icon(
                    painter = painterResource(id = R.drawable.down), // Icône flèche vers le bas
                    contentDescription = "Menu déroulant",
                    modifier = Modifier.size(24.dp),
                    tint = primary
                )
            }

            // Menu déroulant
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier.background(Color.White) // Fond blanc pour le menu
            ) {
                SessionType.entries.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.name,
                                style = TextStyle(fontSize = 16.sp), // Taille du texte dans le menu
                                color = darkerGrey
                            )
                        },
                        onClick = {
                            selectedSessionType = option
                            isExpanded = false
                        },
                        modifier = Modifier.background(Color.White) // Fond blanc pour chaque élément
                    )
                }
            }

        }
        InputTextField(
            value = repititionController,
            onValueChange = { repititionController = it },
            placeholder = "Répitition",
            icon = painterResource(id = android.R.drawable.ic_input_add),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            style = TextStyle(fontSize = 20.sp),
            text = "Series d'exercice",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.Black
        )
        LazyColumn() {
            items(series) { serie ->
                SerieItem(seriesWithExercise = serie, null)
            }
        }
        Button(
            onClick = {
                viewModel.setCurrentSession(selectedSessionType, repitition = 1)
                navController.navigate("exerciseScreen")
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_input_add),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp),
                    tint = Color.White
                )
                Text("Ajouter une serie", color = Color.White)
            }
        }
    }

}