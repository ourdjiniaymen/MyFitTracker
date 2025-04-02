package fr.uge.myfittracker.ui.games

import kotlin.collections.plus
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.uge.myfittracker.ui.theme.primary
import kotlinx.coroutines.delay
import kotlin.random.Random


// Game state class
data class RunnerGameState(
    val isRunning: Boolean = true,
    val playerY: Float = 0f,
    val playerVelocity: Float = 0f,
    val groundY: Float = 0f,
    val obstacles: List<Obstacle> = emptyList(),
    val score: Int = 0,
    val highScore: Int = 0
)


@Composable
fun EndlessRunnerGame(navController: NavController) {
    // Constants
    val playerWidth = 50f
    val playerHeight = 50f
    val groundHeight = 20f
    val jumpForce = -18f
    val gravity = 1f
    val obstacleSpeed = 8f

    // Convert dp to pixels
    val density = LocalDensity.current
    val canvasWidthPx = with(density) { 400.dp.toPx() }
    val canvasHeightPx = with(density) { 300.dp.toPx() }

    // Game state
    var gameState by remember { mutableStateOf(
        RunnerGameState(
            playerY = canvasHeightPx - playerHeight - groundHeight,
            groundY = canvasHeightPx - groundHeight
        )
    )}

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            if (gameState.isRunning) {
                gameState = updateGameState(
                    gameState,
                    canvasWidthPx,
                    canvasHeightPx,
                    playerHeight,
                    gravity,
                    obstacleSpeed
                )
            }
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures {
                    if (gameState.isRunning && gameState.playerY >= gameState.groundY - playerHeight) {
                        gameState = gameState.copy(playerVelocity = jumpForce)
                    } else if (!gameState.isRunning) {
                        // Reset game
                        gameState = RunnerGameState(
                            playerY = canvasHeightPx - playerHeight - groundHeight,
                            groundY = canvasHeightPx - groundHeight,
                            highScore = maxOf(gameState.score, gameState.highScore)
                        )
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .size(400.dp, 300.dp)
        ) {
            // Draw ground
            drawRect(
                Color.LightGray,
                topLeft = Offset(0f, gameState.groundY),
                size = Size(size.width, groundHeight)
            )

            // Draw player (rectangle)
            drawRect(
                Color.Blue,
                topLeft = Offset(50f, gameState.playerY),
                size = Size(playerWidth, playerHeight)
            )

            // Draw obstacles
            gameState.obstacles.forEach { obstacle ->
                drawRect(
                    Color.Red,
                    topLeft = Offset(obstacle.x, obstacle.y),
                    size = Size(obstacle.width, obstacle.height)
                )
            }
        }

        // Score
        Column(verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(
                    text = "Score: ${gameState.score}",
                    color = Color.Black,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = " | Top: ${gameState.highScore}",
                    color = Color.Black,
                    fontSize = 20.sp,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = White
                ),
                onClick = { navController.popBackStack()  }) {
                Text("Quitter", color = White)
            }
            // Game over screen
            if (!gameState.isRunning) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "GAME OVER",
                        color = Color.Red,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "appuyez pour rejouer",
                        color = Color.Black,
                        fontSize = 20.sp
                    )

                }
            }
        }



    }
}



data class Obstacle(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)

fun updateGameState(
    state: RunnerGameState,
    canvasWidth: Float,
    canvasHeight: Float,
    playerHeight: Float,
    gravity: Float,
    obstacleSpeed: Float
): RunnerGameState {
    // Gravité
    val newVelocity = state.playerVelocity + gravity
    val newPlayerY = (state.playerY + newVelocity)
        .coerceAtMost(state.groundY - playerHeight) // Ne pas Tomber

    // Translation Obstacles
    val newObstacles = state.obstacles
        .map { it.copy(x = it.x - obstacleSpeed) }
        .filter { it.x + it.width > 0 }

    // Spawn  obstacles (1% proba par frame)
    val maybeNewObstacle = if (Random.nextFloat() < 0.005f) {
        Obstacle(
            x = canvasWidth,
            y = state.groundY - Random.nextInt(30, 60).toFloat(), // Spawn on ground
            width = Random.nextInt(30, 40).toFloat(),
            height = Random.nextInt(20, 50).toFloat()
        )
    } else null

    // detection de Collision
    val isCollision = newObstacles.any { obstacle ->
        val playerLeft = 50f
        val playerRight = playerLeft + 50f
        val playerTop = newPlayerY
        val playerBottom = playerTop + playerHeight

        playerRight > obstacle.x &&
                playerLeft < obstacle.x + obstacle.width &&
                playerBottom > obstacle.y &&
                playerTop < obstacle.y + obstacle.height
    }

    return state.copy(
        playerY = newPlayerY,
        playerVelocity = if (newPlayerY >= state.groundY - playerHeight) 0f else newVelocity,
        obstacles = newObstacles + listOfNotNull(maybeNewObstacle),
        isRunning = !isCollision,
        score = if (isCollision) state.score else state.score + 1
    )
}

