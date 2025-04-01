package fr.uge.myfittracker.ui.games
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.os.Process
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import fr.uge.myfittracker.MainActivity

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    MemoryGame()
//                }
//            }
//        }
//    }
//}

@Composable
fun MemoryGame() {
    val scope = rememberCoroutineScope()
    var cards = remember { mutableStateListOf(*createShuffledCards()) }
    var flippedIndices by remember { mutableStateOf(setOf<Int>()) }
    var matchedPairs by remember { mutableStateOf(0) }
    var isClickable by remember { mutableStateOf(true) }
    val context = LocalContext.current
    // fonction de RESET
//    fun hardReset() {
//        context.restartApp()
//    }
    fun resetGame() {
        cards.clear()
        cards.addAll(createShuffledCards())
        flippedIndices = emptySet()
        matchedPairs = 0
        isClickable = true
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //titre
        Text(
            text = "Memory Cards",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = "Trouve toutes les paires ",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom =18.dp)
        )

        // Nombre de Paires
        Text(
            text = "Paires trouvées : $matchedPairs/8",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Matrice des cartes
        for (i in 0 until 4) {
            Row {
                for (j in 0 until 4) {
                    val index = i * 4 + j
                    MemoryCard(
                        card = cards[index],
                        isFlipped = flippedIndices.contains(index) || cards[index].isMatched,
                        onClick = {
                            if (isClickable && !cards[index].isMatched && flippedIndices.size < 2) {
                                flippedIndices = flippedIndices + index
                                if (flippedIndices.size == 2) {
                                    isClickable = false
                                    scope.launch {
                                        delay(1000)
                                        val (firstIndex, secondIndex) = flippedIndices.toList()
                                        val isMatch = cards[firstIndex].id == cards[secondIndex].id

                                        if (isMatch) {
                                            cards[firstIndex].isMatched = true
                                            cards[secondIndex].isMatched = true
                                            matchedPairs++
                                        }
                                        flippedIndices = emptySet()
                                        isClickable = true
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }


        // message de Victoire
        if (matchedPairs == 8) {
            Text("Victoire!", fontSize = 24.sp, color = Color.Green)
        }

        //Button Reset
        Button(onClick = { resetGame()  }) {
            Text("RESET")
        }

    }
}

@Composable
fun MemoryCard(
    card: CardData,
    isFlipped: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)
            .clickable(onClick = onClick)
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = if (isFlipped) card.emoji else "?",
                fontSize = 30.sp,
                color = if (isFlipped) Color.Black else Color.Gray
            )
        }
    }
}

data class CardData(
    val id: Int,
    val emoji: String,
    var isMatched: Boolean = false
)

// generer des cartes Randoms ( shuffled )
private fun createShuffledCards(): Array<CardData> {
    val emojis = listOf("🐶", "🐱", "🐭", "🐹", "🐰", "🦊", "🐻", "🐼")
    val pairs = emojis.flatMap { listOf(CardData(it.hashCode(), it), CardData(it.hashCode(), it)) }
    return pairs.shuffled().toTypedArray()
}
//reset
fun Context.restartApp() {
    val intent = Intent(this, MainActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
    startActivity(intent)
    if (this is Activity) {
        finish()
    }
    Runtime.getRuntime().exit(0)
}
@Preview(showBackground = true)
@Composable
fun MemoryPreview()
{
    MemoryGame()
}