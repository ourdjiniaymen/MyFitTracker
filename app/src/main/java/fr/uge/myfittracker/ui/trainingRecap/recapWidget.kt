package fr.uge.myfittracker.ui.trainingRecap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.myfittracker.R
import fr.uge.myfittracker.data.model.SeriesStat
import fr.uge.myfittracker.data.model.SeriesWithExercise
import fr.uge.myfittracker.data.model.SessionWithSeries
import fr.uge.myfittracker.ui.theme.accent
import fr.uge.myfittracker.ui.theme.black
import fr.uge.myfittracker.ui.theme.darkGrey
import fr.uge.myfittracker.ui.theme.primary
import kotlin.random.Random

@Composable
fun SerieStatItem(seriesStat: SeriesStat) {

    var goal = 0
    var achieved = 0
    if (seriesStat.series.series.repetition != null){
        goal = seriesStat.series.series.repetition
        achieved = seriesStat.achievedScore
    }
    else if(seriesStat.series.series.duration != null){
        goal = seriesStat.series.series.duration
        achieved = seriesStat.achievedScore
    }
    val isCompleted:Boolean = achieved >= goal

    var color = primary
    if (!seriesStat.isCompleted) {
        color = accent
    }
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
                .background(color) // Couleur de la barre
        )
        Row {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = seriesStat.series.exercise.name,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        color = black
                    )
                }
                Row{
                    Text(
                        text = "Achieved: ${seriesStat.achievedScore}",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                        color = darkGrey
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (seriesStat.series.series.repetition != null){
                        Text(
                            text = "Goal: ${seriesStat.series.series.repetition}",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                            color = darkGrey
                        )
                    }
                    else{
                        Text(
                            text = "Goal: ${seriesStat.series.series.duration}",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                            color = darkGrey
                        )
                    }
                }
            }
            if (isCompleted){
                Image(
                    painter = painterResource(id = R.drawable.prix), // Remplace avec ton image
                    contentDescription = "Image ronde",
                    modifier = Modifier
                        .size(100.dp) // Taille de l'image
                        .clip(CircleShape) // Applique la forme circulaire
                        .padding(2.dp)
                )
            }
        }

    }
}