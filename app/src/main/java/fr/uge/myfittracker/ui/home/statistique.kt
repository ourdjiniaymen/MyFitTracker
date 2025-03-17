package fr.uge.myfittracker.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun StatistiqueScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Statistique", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Graphique en barres
        BarChart()

        Spacer(modifier = Modifier.height(16.dp))

        // Graphique en lignes
        LineChart()
    }
}

// Graphique en barres avec Compose
@Composable
fun BarChart() {
    val data = listOf(10f, 20f, 15f, 25f, 30f)
    val labels = listOf("Jan", "Feb", "Mar", "Apr", "May") // Labels pour les mois

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val barWidth = size.width / (data.size * 2)
        val maxData = data.maxOrNull() ?: 1f

        // Dessin des barres
        data.forEachIndexed { index, value ->
            drawRect(
                color = Color.Blue,
                topLeft = Offset(x = index * barWidth * 2, y = size.height - (value / maxData) * size.height),
                size = androidx.compose.ui.geometry.Size(barWidth, (value / maxData) * size.height)
            )

            // Dessin des labels au-dessus des barres
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    labels[index], // Label
                    index * barWidth * 2 + barWidth / 2, // Position X (au centre de la barre)
                    size.height - (value / maxData) * size.height - 10, // Position Y (au-dessus de la barre)
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f // Taille du texte
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}


// Graphique en lignes avec Compose
@Composable
fun LineChart() {
    val dataPoints = listOf(5f, 10f, 8f, 15f, 12f)
    val labels = listOf("Point 1", "Point 2", "Point 3", "Point 4", "Point 5") // Labels pour chaque point

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val maxData = dataPoints.maxOrNull() ?: 1f
        val stepX = size.width / (dataPoints.size - 1)
        val stepY = size.height / maxData

        // Dessin des lignes
        dataPoints.zipWithNext().forEachIndexed { index, (start, end) ->
            drawLine(
                color = Color.Red,
                start = Offset(index * stepX, size.height - start * stepY),
                end = Offset((index + 1) * stepX, size.height - end * stepY),
                strokeWidth = 4f
            )
        }

        // Dessin des labels sur les points
        dataPoints.forEachIndexed { index, value ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    labels[index], // Label
                    index * stepX, // Position X du point
                    size.height - value * stepY - 10, // Position Y juste au-dessus du point
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 40f // Taille du texte
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

