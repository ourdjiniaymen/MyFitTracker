package fr.uge.myfittracker.ui.creation

import android.widget.Space
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
import fr.uge.myfittracker.data.model.SeriesWithExercise
import fr.uge.myfittracker.ui.theme.black
import fr.uge.myfittracker.ui.theme.colorPalette
import fr.uge.myfittracker.ui.theme.darkGrey
import kotlin.random.Random


@Composable
fun SerieItem(seriesWithExercise: SeriesWithExercise) {
    val randomColor = colorPalette[Random.nextInt(0, colorPalette.size)]
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
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
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start){
                Text(
                    text = seriesWithExercise.exercise.name,
                    style = TextStyle(fontSize = 20.sp,  fontWeight = FontWeight.Bold),
                    color = darkGrey
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    if(seriesWithExercise.series.duration!= null){
                        "${seriesWithExercise.series.duration} secondes"
                    }else{
                        "X ${seriesWithExercise.series.repetition}"
                    },
                    style = TextStyle(fontSize = 16.sp),
                    color = darkGrey
                )
            }
            Text(
                text = seriesWithExercise.exercise.description!!,
                style = TextStyle(fontSize = 16.sp),
                color = black
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
