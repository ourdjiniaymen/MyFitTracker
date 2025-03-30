package fr.uge.myfittracker.ui.creation

import Step
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun ExerciseScreen(navController: NavController, viewModel: SeriesWithExerciseViewModel) {
    var titleController by remember { mutableStateOf(TextFieldValue("")) }
    var descriptionController by remember { mutableStateOf(TextFieldValue("")) }
    var repititionController by remember { mutableStateOf(TextFieldValue("")) }
    var durationController by remember { mutableStateOf(TextFieldValue("")) }
    var isDurationVisible by remember { mutableStateOf(false) }

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
                        viewModel.setCurrentExercise(titleController.text, descriptionController.text)
                        if (isDurationVisible){
                            viewModel.setCurrentSeries(null, durationController.text.toIntOrNull())
                        }
                        else{
                            viewModel.setCurrentSeries(repititionController.text.toIntOrNull(), null)
                        }
                        navController.popBackStack()
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
            text = "Exercice",
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
            text = "Détails de la série",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.Black
        )
        Button(
            onClick = { isDurationVisible = !isDurationVisible },
            modifier = Modifier.width(100.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color.White
            )
        ) {
            Text(if (isDurationVisible) "Durée" else "Répitition")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isDurationVisible){
            InputTextField(
                value = durationController,
                onValueChange = { durationController = it },
                placeholder = "Durée",
                icon = painterResource(id = android.R.drawable.ic_input_add),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        else {
            InputTextField(
                value = repititionController,
                onValueChange = { repititionController = it },
                placeholder = "Répitition",
                icon = painterResource(id = android.R.drawable.ic_input_add),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }



    }
}

@Composable
fun InputTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    icon: Any,
    keyboardOptions: KeyboardOptions  = KeyboardOptions(keyboardType = KeyboardType.Text),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            keyboardOptions = keyboardOptions,
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