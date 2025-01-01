package fr.uge.myfittracker.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.myfittracker.ui.theme.*
import kotlinx.coroutines.delay
import java.util.*
import java.text.SimpleDateFormat

@Composable
fun HomeScreen() {
    val currentTime = remember { mutableStateOf(getCurrentTime()) }
    val currentDate = remember { mutableStateOf(getCurrentDate()) }

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
        TopBar()
        CurrentDateTime(currentTime.value,currentDate.value)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomizedCircularProgressIndicator(
                modifier = Modifier
                    .size(250.dp)
                    //.background(color = if (darkTheme) Color.Black else Color.White),
                    .background(Color.Transparent),
                initialValue = 30,
                primaryColor = primary,
                secondaryColor = Color.LightGray,
                circleRadius = 230f,
                onPositionChange = {

                }
            )
            Text(
                text = "520",
                color = Color.Black,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "pas effectués",
                color = Color.Gray,
            )
        }
        ValidateButton(){}
        Statistics(
            distance= 5,
            calories= 10,
            points= 20
        )
    }

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
fun TopBar(){
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
                Text("200 ★", color = secondary, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {}
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

@Composable
fun Statistics(distance: Int, calories: Int, points: Int) {
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
                Text(text = "$distance Km", style = MaterialTheme.typography.titleMedium)
                Text(text = "distance", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            VerticalDivider(color = Color.Gray)
            // Calories
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$calories kCal", style = MaterialTheme.typography.titleMedium)
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

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}