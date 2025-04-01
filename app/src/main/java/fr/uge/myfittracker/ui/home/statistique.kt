package fr.uge.myfittracker.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.myfittracker.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.tooling.preview.Preview


@Composable
@Preview(showBackground = true)
fun StatistiqueScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Permet le scroll vertical si besoin
    ) {
        // Image de profil et nom de l'utilisateur
        val profileImage: Painter = painterResource(id = R.drawable.profile_placeholder)
        Image(
            painter = profileImage,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(50))
                .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Nom de l'utilisateur",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cartes informatives
        repeat(3) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Distance parcourue : 5 km",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Statistiques",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Graphique en barres
        BarChart()

        Spacer(modifier = Modifier.height(32.dp))
        BarChartcalorie()
    }
}

@Composable
fun BarChart() {
    val data = listOf(5000f, 7000f, 6000f, 8000f, 7500f, 9000f, 6500f) // Nombre de pas
    val labels = listOf("Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim") // Jours de la semaine

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val barWidth = size.width / (data.size * 3) // Plus fines
        val maxData = data.maxOrNull() ?: 1f

        // Ajouter le titre "Nombre de pas" au-dessus du graphe
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "Nombre de pas",
                size.width / 2,
                5f, // Position en haut du graphe
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 50f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }


        data.forEachIndexed { index, value ->
            val barHeight = (value / maxData) * size.height
            val xPosition = index * barWidth * 3 + barWidth

            // Dessin des barres
            drawRect(
                color = Color(0xFF90CAF9), // Bleu clair
                topLeft = Offset(x = xPosition, y = size.height - barHeight),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
            )

            // Affichage du nombre de pas sur la barre
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    value.toInt().toString(), // Affichage du nombre de pas
                    xPosition + barWidth / 2, // Centrage
                    size.height - barHeight - 10, // Position au-dessus de la barre
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }

            // Affichage des labels des jours
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    labels[index],
                    xPosition + barWidth / 2,
                    size.height - 10, // En bas des barres
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}


@Composable
fun BarChartcalorie() {
    val data = listOf(1000f, 700f, 600f, 800f, 750f, 900f, 650f) // Nombre de pas
    val labels = listOf("Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim") // Jours de la semaine

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val barWidth = size.width / (data.size * 3) // Plus fines
        val maxData = data.maxOrNull() ?: 1f

        // Ajouter le titre "Nombre de pas" au-dessus du graphe
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "Calories brûlées",
                size.width / 2,
                5f, // Position en haut du graphe
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 50f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }


        data.forEachIndexed { index, value ->
            val barHeight = (value / maxData) * size.height
            val xPosition = index * barWidth * 3 + barWidth

            // Dessin des barres
            drawRect(
                color = Color(0xFF90CAF9), // Bleu clair
                topLeft = Offset(x = xPosition, y = size.height - barHeight),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
            )

            // Affichage du nombre de pas sur la barre
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    value.toInt().toString(), // Affichage du nombre de pas
                    xPosition + barWidth / 2, // Centrage
                    size.height - barHeight - 10, // Position au-dessus de la barre
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }

            // Affichage des labels des jours
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    labels[index],
                    xPosition + barWidth / 2,
                    size.height - 10, // En bas des barres
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

