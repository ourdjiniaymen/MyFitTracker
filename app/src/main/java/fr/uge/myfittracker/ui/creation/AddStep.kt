package fr.uge.myfittracker.ui.creation
/*
import Step
import StepType
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.uge.myfittracker.ui.training.ExerciseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.uge.myfittracker.ui.theme.primary
import fr.uge.myfittracker.R
import fr.uge.myfittracker.ui.theme.darkerGrey
import fr.uge.myfittracker.ui.theme.secondary
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepScreen(navController: NavController, viewModel: ExerciseViewModel) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var notes by remember { mutableStateOf(TextFieldValue("")) }
    var objective by remember { mutableStateOf(TextFieldValue("")) }
    var isError by remember { mutableStateOf(false) }

    // État pour le menu déroulant
    var isExpanded by remember { mutableStateOf(false) }
    var selectedStepType by remember { mutableStateOf(StepType.WORKOUT) }
    var durationOrRepetitionType by remember { mutableStateOf(DurationOrRepetitionType.Duration) }
    var isDurationOrRepetitionExpanded by remember { mutableStateOf(false) }
    val steps by viewModel.steps.collectAsState()
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Ajouter Etape",
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
                    onClick =  {
                        val objectiveInt = objective.text.toIntOrNull()
                        if (objectiveInt != null) {
                            isError = false
                            val newStep = Step(
                                type = selectedStepType,
                                title = title.text,
                                notes = notes.text.split(","),
                                durationOrRepetition = durationOrRepetitionType,
                                objective = objectiveInt
                            )
                            Log.i("before step", steps.toString())
                            viewModel.addStep(newStep)
                            navController.popBackStack()
                        } else {
                            isError = true
                        }
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
            text = "Aperçu",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.Black
        )

        // Menu déroulant pour StepType
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
                    text = selectedStepType.name,
                    modifier = Modifier.weight(1f),
                    color = if (selectedStepType == StepType.WORKOUT) darkerGrey else Color.Black
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
                StepType.entries.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.name,
                                style = TextStyle(fontSize = 16.sp), // Taille du texte dans le menu
                                color = darkerGrey
                            )
                        },
                        onClick = {
                            selectedStepType = option
                            isExpanded = false
                        },
                        modifier = Modifier.background(Color.White) // Fond blanc pour chaque élément
                    )
                }
            }
        }

        // Champ pour les notes
        InputTextField(
            value = notes,
            onValueChange = { notes = it },
            placeholder = "Notes",
            icon = painterResource(id = android.R.drawable.ic_input_add) // Icône personnalisée
        )

        // Titre "Détails Etape"
        Text(
            style = TextStyle(fontSize = 20.sp),
            text = "Détails Etape",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.Black
        )

        // Champ pour le titre
        InputTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = "Titre",
            icon = painterResource(id = R.drawable.up_down_icon) // Icône personnalisée
        )

        ExposedDropdownMenuBox(
            expanded = isDurationOrRepetitionExpanded,
            onExpandedChange = { isDurationOrRepetitionExpanded = it },
        ) {
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
                    text = durationOrRepetitionType.name,
                    modifier = Modifier.weight(1f),
                    color = darkerGrey
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
                expanded = isDurationOrRepetitionExpanded,
                onDismissRequest = { isDurationOrRepetitionExpanded = false },
                modifier = Modifier.background(Color.White) // Fond blanc pour le menu
            ) {
                DurationOrRepetitionType.entries.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.name,
                                style = TextStyle(fontSize = 16.sp), // Taille du texte dans le menu
                                color = darkerGrey
                            )
                        },
                        onClick = {
                            durationOrRepetitionType = option
                            isDurationOrRepetitionExpanded = false
                        },
                        modifier = Modifier.background(Color.White) // Fond blanc pour chaque élément
                    )
                }
            }
        }
        // Champ pour l'objectif
        InputTextField(
            value = objective,
            onValueChange = { objective = it },
            placeholder = "Objectif",
            icon = painterResource(id = R.drawable.down) // Icône personnalisée
        )

        // Affichage d'une erreur si l'objectif n'est pas valide
        if (isError) {
            Text(
                text = "Veuillez entrer un nombre valide.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}*/