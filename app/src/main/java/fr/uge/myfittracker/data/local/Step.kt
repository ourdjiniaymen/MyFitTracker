import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

// Enum pour le type d'étape
enum class StepType {
    WORKOUT,
    WARM_UP,
    COOL_DOWN,
    REST
}

// Enum pour le type d'objectif (durée ou répétition)
enum class DurationOrRepetitionType {
    Duration,
    Repetition
}

// Modèle Step
data class Step(
    val type: StepType,
    val title: String,
    val notes: List<String>,
    val durationOrRepetition: DurationOrRepetitionType,
    val objective: Int // Objectif (entier)
) {
    // Fonction pour formater l'objectif
    fun objectiveToString(): String {
        return when (durationOrRepetition) {
            DurationOrRepetitionType.Repetition -> "$objective reps" // Format pour les répétitions
            DurationOrRepetitionType.Duration -> {
                // Convertir l'objectif (en secondes) en une durée formatée
                val duration = objective.toDuration(DurationUnit.SECONDS)
                formatDuration(duration)
            }
        }
    }

    // Fonction pour formater la durée
    private fun formatDuration(duration: Duration): String {
        val hours = duration.inWholeHours
        val minutes = duration.inWholeMinutes % 60
        val seconds = duration.inWholeSeconds % 60

        return when {
            hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, seconds) // Format HH:MM:SS
            else -> String.format("%02d:%02d", minutes, seconds) // Format MM:SS
        }
    }
}

// Modèle pour gérer la durée ou les répétitions
sealed class DurationOrRepetition {
    data class Duration(val duration: kotlin.time.Duration) : DurationOrRepetition()
    data class Repetition(val count: Int) : DurationOrRepetition()
}