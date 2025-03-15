package fr.uge.myfittracker.ui.creation

import Step
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.myfittracker.ui.training.ExerciseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.uge.myfittracker.R
import fr.uge.myfittracker.data.local.Exercise
import fr.uge.myfittracker.ui.theme.black
import fr.uge.myfittracker.ui.theme.colorPalette
import fr.uge.myfittracker.ui.theme.darkGrey
import fr.uge.myfittracker.ui.theme.darkerGrey
import fr.uge.myfittracker.ui.theme.primary
import fr.uge.myfittracker.ui.theme.secondary
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(navController: NavController, viewModel: ExerciseViewModel) {
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    var titleController by remember { mutableStateOf(TextFieldValue(title)) }
    var descriptionController by remember { mutableStateOf(TextFieldValue(description)) }
    val steps by viewModel.steps.collectAsState()
    Log.i("test viewmodel", steps.toString())
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Nouvel Exercice",
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
                        if (steps.isNotEmpty()) {
                            viewModel.saveExercise(title, description)
                            navController.popBackStack()
                        } else {
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

        InputTextField(
            value = titleController,
            onValueChange = {titleController = it },
            placeholder = "Titre",
            icon = painterResource(id = android.R.drawable.ic_input_add)
        )

        InputTextField(
            value = descriptionController,
            onValueChange = { descriptionController = it },
            placeholder = "Description",
            icon = painterResource(id = android.R.drawable.ic_input_add)
        )
        Text(
            style = TextStyle(fontSize = 20.sp),
            text = "Etapes",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.Black
        )
        // Liste des étapes
        LazyColumn {
            items(steps) { step ->
                StepItem(step)
            }
        }

        // Bouton Ajouter Étape
        Button(
            onClick = {
                viewModel.updateTitle(titleController.text)
                viewModel.updateDescription(descriptionController.text)
                navController.navigate("stepScreen")
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
                Text("Étape", color = Color.White)
            }
        }
    }
}

@Composable
fun InputTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    icon: Any
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            decorationBox = { innerTextField ->
                if (value.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = darkerGrey
                    )
                }
                innerTextField()
            }
        )
        Icon(
            painter = icon as androidx.compose.ui.graphics.painter.Painter,
            contentDescription = "Icon",
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp),
            tint = primary
        )
    }
}

@Composable
fun StepItem(step: Step) {
    val randomColor = colorPalette[Random.nextInt(0, colorPalette.size)]
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
            .padding(16.dp)
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(8.dp) // Largeur de la barre
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .background(randomColor) // Couleur de la barre
        )

        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(
                text = step.type.name,
                style = TextStyle(fontSize = 16.sp),

                color = darkGrey
            )
            Text(
                text = step.title,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),

                color = black
            )
            Text(
                text = step.objectiveToString(),
                style = TextStyle(fontSize = 16.sp),

                color = darkGrey
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.right_icon), // Icône flèche vers le bas
            contentDescription = "Menu déroulant",
            modifier = Modifier.size(24.dp),
            tint = darkGrey
        )
    }
}