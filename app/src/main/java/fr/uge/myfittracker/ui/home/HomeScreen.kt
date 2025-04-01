package fr.uge.myfittracker.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.uge.myfittracker.ui.home.viewmodel.HomeViewModel
import fr.uge.myfittracker.ui.home.viewmodel.StepCounterViewModel
import fr.uge.myfittracker.ui.theme.*
import kotlinx.coroutines.delay
import java.util.*
import java.text.SimpleDateFormat

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {

    val dailyStepsGoal by homeViewModel.dailyStepsGoal.collectAsState()

    val dailySteps by homeViewModel.dailySteps.collectAsState()
    Log.d("HomeScreen", "Pas actuels: $dailySteps") // Vérifier que les valeurs changent

    val dailyStars by homeViewModel.dailyStars.collectAsState()
    val dailyLevel by homeViewModel.dailyLevel.collectAsState()

    val totalSteps by homeViewModel.totalSteps.collectAsState()
    val totalStars by homeViewModel.totalStars.collectAsState()

    val currentTime = remember { mutableStateOf(getCurrentTime()) }
    val currentDate = remember { mutableStateOf(getCurrentDate()) }

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L) //1 seconde
            currentTime.value = getCurrentTime()
            currentDate.value = getCurrentDate()
        }
    }
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //TopBar(totalStars)
        TopBar(dailyStars)
        CurrentDateTime(currentTime.value,currentDate.value)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomizedCircularProgressIndicator(
                modifier = Modifier
                    .size(250.dp)
                    //.background(color = if (darkTheme) Color.Black else Color.White),
                    .background(Color.Transparent),
                initialValue = homeViewModel.convertStepsToPercentage(dailySteps, dailyStepsGoal),
                primaryColor = primary,
                secondaryColor = Color.LightGray,
                circleRadius = 230f,
                onPositionChange = {

                }
            )
            Text(
                text = "$dailySteps",
                color = Color.Black,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "pas effectués sur $dailyStepsGoal",
                color = Color.Gray,
            )
        }
        ValidateButton(){
            //homeViewModel.incrementSteps(500) //just for test
            if ((dailyLevel == 0)  ) {
                if (dailySteps >= dailyStepsGoal * 0.20){
                    homeViewModel.incrementLevel(1)
                    homeViewModel.incrementStars(2)
                    dialogTitle = "Bravo !"
                    dialogText = "Vous venez d'atteindre le niveau 1 et de mériter 2★"
                } else {
                    dialogTitle = "Encore un petit effort !"
                    dialogText = "Encore ${((dailyStepsGoal * 0.20) - dailySteps).toInt()} pas pour atteindre le niveau suivant et gagner 2★ de plus"
                }
                showDialog = true
            } else if ((dailyLevel == 1) ) {
                if (dailySteps >= dailyStepsGoal * 0.50) {
                    homeViewModel.incrementLevel(1)
                    homeViewModel.incrementStars(5)
                    dialogTitle = "Bravo !"
                    dialogText = "Vous venez d'atteindre le niveau 2 et de mériter 5★"
                } else {
                    dialogTitle = "Encore un petit effort !"
                    dialogText = "Encore ${((dailyStepsGoal * 0.50) - dailySteps).toInt()} pas pour atteindre le niveau suivant et gagner 5★ de plus"
                }
                showDialog = true
            }else if ((dailyLevel == 2) ) {
                if (dailySteps >= dailyStepsGoal * 0.80){
                    homeViewModel.incrementLevel(1)
                    homeViewModel.incrementStars(8)
                    dialogTitle = "Bravo !"
                    dialogText = "Vous venez d'atteindre le niveau 3 et de mériter 8★"
                } else {
                    dialogTitle = "Encore un petit effort !"
                    dialogText = "Encore ${((dailyStepsGoal * 0.80) - dailySteps).toInt()} pas pour atteindre le niveau suivant et gagner 8★ de plus"
                }
                showDialog = true
            }
        }
        // Show le Dialog if showDialog is true
        if (showDialog) {
            MinimalDialog(title = dialogTitle, text = dialogText) {
                showDialog = false // click anywhere to close dialog
            }
        }

        Statistics(
            distance= homeViewModel.calculateDistance(dailySteps), //dailyDistance,
            calories= homeViewModel.calculateCalories(dailySteps), //dailyCalories
            points= dailyStars
        )
    }

}

fun convertStepsToPercentage(currentSteps: Int, goalSteps: Int): Int {
    if (goalSteps == 0) return 0
    val percentage = (currentSteps * 100) / goalSteps
    return if (percentage > 100) 100 else percentage
}

fun getCurrentTime(): String {
    val currentDate = Calendar.getInstance().time
    val timeFormat = SimpleDateFormat("a hh:mm:ss", Locale.FRANCE) //Locale.getDefault()
    return timeFormat.format(currentDate)
}

fun getCurrentDate(): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE) //Locale.getDefault()
    return dateFormat.format(currentDate)
}

@Composable
fun CurrentDateTime(
    time:String,
    date:String
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = time,
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = date,
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TopBar(stars: Int){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            //.background(color = if (darkTheme) Color.Black else Color.White),
            .background(color = Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "MyFitTracker",
            color = primary,
            fontStyle = FontStyle.Italic,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box (
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = secondary.copy(alpha = 0.2f))
                    .padding(horizontal = 16.dp)
            ){
                Text("$stars ★", color = secondary, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    //TODO : goto the profile page
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Icon",
                    tint = Color.Black
                )
            }

        }

    }
}

@Composable
fun ValidateButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.5f),
        colors = ButtonDefaults.buttonColors(primary)
    ) {
        Text(
            text = "Valider mes pas",
            color = Color.White
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun Statistics(distance: Double, calories: Double, points: Int) {
    Box (
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(color = Color.LightGray.copy(alpha = 0.3f))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Distance
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = String.format("%.2f Km", distance), style = MaterialTheme.typography.titleMedium)
                Text(text = "distance", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            VerticalDivider(color = Color.Gray)
            // Calories
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = String.format("%.2f kCal", calories), style = MaterialTheme.typography.titleMedium)
                Text(text = "calories", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            VerticalDivider(color = Color.Gray)
            // Récompenses
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$points ★", style = MaterialTheme.typography.titleMedium)
                Text(text = "gagnés", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}

@Composable
fun MinimalDialog(title: String, text: String, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .padding(16.dp)
                .background(Color.Transparent),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if(title == "Bravo !")
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Profile Icon",
                        tint = primary
                    )
                else
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Profile Icon",
                        tint = primary
                    )
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = text,
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    //fontWeight = FontWeight.Bold
                )
                if(title == "Bravo !")
                    Button(
                        onClick = {
                            //TODO: Go to the game page
                        },
                        modifier = Modifier.fillMaxWidth(0.6f),
                        colors = ButtonDefaults.buttonColors(primary)
                    ) {
                        Text(
                            text = "Débloquer le jeu",
                            color = Color.White
                        )
                    }
                else
                    Button(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier.fillMaxWidth(0.6f),
                        colors = ButtonDefaults.buttonColors(primary)
                    ) {
                        Text(
                        text = "OK",
                        color = Color.White
                        )
                    }
            }

        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    //HomeScreen(HomeViewModel())
    MinimalDialog(title="Bravo !", text="hahahahahahh"){}
}