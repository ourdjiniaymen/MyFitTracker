    package fr.uge.myfittracker.ui.games
    import android.content.Context
    import fr.uge.myfittracker.R
    import android.media.MediaPlayer
    import android.os.Bundle
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.Button
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import kotlinx.coroutines.delay
    import kotlin.random.Random
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.ui.graphics.Color.Companion.White
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.navigation.NavController
    import fr.uge.myfittracker.ui.theme.primary

    //    class MainActivity : ComponentActivity() {
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContent {
//                MaterialTheme {
//                    Surface(modifier = Modifier.fillMaxSize()) {
//                        ReactionTimeGame()
//                    }
//                }
//            }
//        }
//    }

    enum class GameState {
        WAITING, READY, GO, TOO_SOON, RESULT, JUMPSCARE
    }

    @Composable
    fun ReactionTimeGame(navController: NavController) {
        val context = LocalContext.current
        var gameState by remember { mutableStateOf(GameState.WAITING) }
        var reactionTime by remember { mutableStateOf(0L) }
        var startTime by remember { mutableStateOf(0L) }
        var countdown by remember { mutableStateOf(3) }
        var bestTime by remember { mutableStateOf<Long?>(null) }
        var showJumpScare by remember { mutableStateOf(false) }
        var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

        // Clean up media player when composable is disposed
        DisposableEffect(Unit) {
            onDispose {
                mediaPlayer?.release()
            }
        }

        // Launch effect for game state changes
        LaunchedEffect(gameState) {
            when (gameState) {
                GameState.READY -> {
                    // Countdown from 3
                    repeat(3) {
                        delay(1000)
                        countdown--
                    }
                    // Random delay before GO (1-3 seconds)
                    delay(Random.nextLong(1000, 3000))
                    startTime = System.currentTimeMillis()
                    gameState = GameState.GO
                }

                GameState.TOO_SOON -> {
                    // Play jump scare sound
                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer.create(context, R.raw.wegajumpscare).apply {
                        start()
                    }
                    showJumpScare = true
                    delay(2000) // Show jump scare for 1 second
                    showJumpScare = false
                    gameState = GameState.WAITING
                }

                else -> {}
            }
        }

        // Background color based on state
        val backgroundColor = when (gameState) {
            GameState.WAITING -> Color.LightGray
            GameState.READY -> Color.Yellow
            GameState.GO -> Color.Green
            GameState.TOO_SOON -> Color.Red
            GameState.RESULT -> Color.White
            GameState.JUMPSCARE -> Color.Black
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (showJumpScare) Color.Black else backgroundColor)
                .clickable(
                    enabled = gameState == GameState.READY,
                    onClick = { gameState = GameState.TOO_SOON }
                ),
            contentAlignment = Alignment.Center
        ) {
            when {
                showJumpScare -> {
                    Image(
                        painter = painterResource(id = R.drawable.jumpscare2),
                        contentDescription = "Jump Scare",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (gameState) {
                        GameState.WAITING -> {
                            Text("Test de temps de réaction ", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(onClick = {
                                gameState = GameState.READY
                                countdown = 3
                            }) {
                                Text("Commencer le Test")
                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = primary,
                                    contentColor = White
                                ),
                                onClick = { navController.popBackStack()  }) {
                                Text("Quitter")
                            }
                        }

                        GameState.READY -> {
                            Text(" Soyez prêts ...", fontSize = 24.sp)
                            Text("$countdown", fontSize = 48.sp, fontWeight = FontWeight.Bold)
                        }

                        GameState.GO -> {
                            Text("CLIQUEZ MAINTENANT!", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(onClick = {
                                reactionTime = System.currentTimeMillis() - startTime
                                gameState = GameState.RESULT
                                bestTime = bestTime?.let { minOf(it, reactionTime) } ?: reactionTime
                            }) {
                                Text("CLIQUEZ-MOI !")
                            }
                        }

                        GameState.RESULT -> {
                            Text("Temps:", fontSize = 24.sp)
                            Text("${reactionTime} ms", fontSize = 48.sp, fontWeight = FontWeight.Bold)
                            bestTime?.let {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Meilleur Temps: ${it} ms", fontSize = 20.sp)
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(onClick = {
                                gameState = GameState.WAITING
                            }) {
                                Text("Ressayer")
                            }
                            Button(onClick = { navController.popBackStack()  }) {
                                Text("Quitter")
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

