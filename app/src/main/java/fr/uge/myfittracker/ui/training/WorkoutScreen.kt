package fr.uge.myfittracker.ui.training

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithExercise

import android.os.CountDownTimer
import android.util.Log
import android.widget.NumberPicker
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import fr.uge.myfittracker.R
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.SeriesStat
import fr.uge.myfittracker.data.model.Session
import fr.uge.myfittracker.data.model.SessionStat
import fr.uge.myfittracker.data.model.SessionType
import fr.uge.myfittracker.data.model.SessionWithSeries
import fr.uge.myfittracker.ui.theme.primary
import fr.uge.myfittracker.ui.theme.secondary
import fr.uge.myfittracker.ui.theme.textPrimary
import fr.uge.myfittracker.ui.theme.textSecondary
import fr.uge.myfittracker.ui.training.viewmodel.TrainingPlanViewModel
import fr.uge.myfittracker.utils.HelperFunction
import androidx.compose.ui.graphics.Color.Companion.White as White

@Composable
fun WorkoutScreen(navController: NavHostController, trainingPlanViewModel: TrainingPlanViewModel) {
    val sessionWithSeries = trainingPlanViewModel.currentSession.collectAsState().value
    val listSeries = mutableListOf<SeriesWithExercise>()
    repeat(sessionWithSeries?.session!!.repetition){
        listSeries.addAll(sessionWithSeries.series)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        val listSeriesStat = remember { mutableStateListOf<SeriesStat>() }
        var currentSeriesIndex by remember { mutableIntStateOf(0) }
        val currentSeries = listSeries[currentSeriesIndex] //sessionWithSeries!!.series[currentSeriesIndex]
        val isTimeBased = currentSeries.series.duration != null
        val onSessionFinished = {
            val sessionStat = SessionStat(
                session = sessionWithSeries.session,
                seriesStat = listSeriesStat
            )
            trainingPlanViewModel.setSessionStat(sessionStat)
            // todo : save session stat in db
            Log.d("Session stat", sessionStat.toString())
            navController.navigate("sessionRecap")
        }

        SeriesExecutionScreen(
            exerciseName = currentSeries.exercise.name,
            previousExerciseName = if (currentSeriesIndex > 0) listSeries[currentSeriesIndex].exercise.name else null,
            exerciseDescription = currentSeries.exercise.description,
            isTimeBased = isTimeBased,
            duration = if (isTimeBased) currentSeries.series.duration else null,
            repetitions = if (!isTimeBased) currentSeries.series.repetition else null,
            onPrevious = {
                if (currentSeriesIndex > 0) currentSeriesIndex--
            },
            onSkip = { achievedScore ->
                Log.i("list series*****", listSeriesStat.toString())
                listSeriesStat +=
                    SeriesStat(
                        series = currentSeries,
                        achievedScore = achievedScore,
                        isCompleted = false
                )
                Log.i("list series", listSeriesStat.toString())
                if (currentSeriesIndex == listSeries.size - 1) {
                    onSessionFinished()
                } else {
                    currentSeriesIndex++
                }
            },
            onExit = {
                navController.navigate("Plan")
            },
            onFinished = {
                Log.i("list series *********", listSeriesStat.toString())
                listSeriesStat+=SeriesStat(
                        series = currentSeries,
                        achievedScore = if (isTimeBased) currentSeries.series.duration
                            ?: 0 else currentSeries.series.repetition ?: 0,
                        isCompleted = true
                )
                Log.i("list series", listSeriesStat.toString())
                if (currentSeriesIndex == listSeries.size - 1) {
                    onSessionFinished()
                } else {
                    currentSeriesIndex++
                }
            }
        )

    }
}

@Composable
fun SeriesExecutionScreen(
    exerciseName: String,
    exerciseDescription: String?,
    previousExerciseName: String?,
    isTimeBased: Boolean,
    duration: Int?,
    repetitions: Int?,
    onPrevious: () -> Unit,
    onSkip: (Int) -> Unit,
    onExit: () -> Unit,
    onFinished: () -> Unit
) {
    var remainingTime by remember { mutableIntStateOf(duration ?: 0) }
    var elapsedTime by remember { mutableIntStateOf(0) }
    var repsDone by remember { mutableIntStateOf(0) }
    var timerRunning by remember { mutableStateOf(false) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogDescription by remember { mutableStateOf("") }
    var onDialogConfirm by remember { mutableStateOf<() -> Unit>({ }) }
    var showAchievedCountDialog by remember { mutableStateOf(false) }
    var onAchievedScoreDialogConfirm by remember { mutableStateOf<(Int) -> Unit>({ }) }
    var maxRepetition by remember { mutableIntStateOf(0) }

    val animatedProgress by animateFloatAsState(
        targetValue = if (isTimeBased) elapsedTime.toFloat() / (duration ?: 1) else 1f,
        label = "Progress Animation"
    )

    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            timer?.cancel()
            timer = object : CountDownTimer(remainingTime * 1000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingTime--
                    elapsedTime++
                }

                override fun onFinish() {
                    elapsedTime = 0
                    remainingTime = duration ?: 0
                    timerRunning = false
                    dialogTitle = "$exerciseName est terminé"
                    dialogDescription = "Je veux passer à l'étape suivante"
                    onDialogConfirm = {
                        onFinished()
                    }
                    showConfirmationDialog = true
                }
            }.start()
        } else {
            timer?.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 72.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConfirmationDialog(
            title = dialogTitle,
            description = dialogDescription,
            showDialog = showConfirmationDialog,
            onDismiss = { showConfirmationDialog = false },
            onConfirm = onDialogConfirm
        )
        AchievedRepetitionDialog(
            showDialog = showAchievedCountDialog,
            maxRepetition = maxRepetition,
            onDismiss = { showAchievedCountDialog = false },
            onConfirm = onAchievedScoreDialogConfirm
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = exerciseName,
                color = textPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(48.dp))

            if (isTimeBased) {
                CustomCircularProgressBar(
                    progress = animatedProgress,
                    value = HelperFunction.durationFormat(remainingTime)
                )
            } else {
                CustomCircularProgressBar(
                    progress = 0f,
                    value = HelperFunction.countFormat(repetitions ?: 0)
                )
            }

            Spacer(Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (isTimeBased)
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary,
                            contentColor = White
                        ),
                        onClick = { timerRunning = !timerRunning },
                        ) {
                        Icon(
                            painterResource(if (timerRunning) R.drawable.ic_pause else R.drawable.ic_play),
                            "Run/Pause",
                            tint = White
                        )
                    }
                if (isTimeBased)
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary,
                            contentColor = White
                        ),
                        onClick = {
                        timerRunning = false
                        dialogTitle = "Restart $exerciseName"
                        dialogDescription = "Êtes-vous sûr de vouloir relancer l'exercice ?"
                        onDialogConfirm = {
                            remainingTime = duration ?: 0
                            elapsedTime = 0
                            repsDone = 0
                            timerRunning = false
                        }
                        if (elapsedTime > 0)
                            showConfirmationDialog = true
                    }) {
                        Icon(painterResource(R.drawable.ic_restart), "Restart", tint = White)
                    }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = White
                    ),
                    onClick = {
                    if (isTimeBased) {
                        timerRunning = false
                        dialogTitle = "Sauter $exerciseName"
                        dialogDescription = "Êtes-vous sûr de vouloir sauter l'exercice ?"
                        onDialogConfirm = {
                            remainingTime = duration ?: 0
                            elapsedTime = 0
                            onSkip(elapsedTime)
                        }
                        showConfirmationDialog = true
                    } else {
                        showAchievedCountDialog = true
                        maxRepetition = repetitions!!
                        onAchievedScoreDialogConfirm = { score ->
                            onSkip(score)
                        }
                    }
                }) {
                    Icon(painterResource(R.drawable.ic_skip), "Skip", tint = White)
                }
            }

            Spacer(Modifier.height(48.dp))
            if (exerciseDescription != null)
                Text(
                    exerciseDescription,
                    color = textSecondary,
                    fontSize = 20.sp
                )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            if (previousExerciseName != null)
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = White
                    ),
                    shape = ShapeDefaults.Small,
                    onClick = {
                        timerRunning = false
                        dialogTitle = "Retour à $previousExerciseName "
                        dialogDescription =
                            "Êtes-vous sûr de vouloir refaire l'exercice précédent ?"
                        onDialogConfirm = onPrevious
                        showConfirmationDialog = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Précedent",
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            if (previousExerciseName != null)

                Spacer(Modifier.width(50.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = White
                ),
                shape = ShapeDefaults.Small,
                onClick = {
                    timerRunning = false
                    dialogTitle = "Abandonner $exerciseName"
                    dialogDescription = "Êtes-vous sûr de vouloir abandonner la session ?"
                    onDialogConfirm = onExit
                    showConfirmationDialog = true
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Abandonner", color = White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

        }
    }
}

@Composable
fun CustomCircularProgressBar(
    progress: Float,
    value: String,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 12f
) {


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(150.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = size.minDimension
            val radius = size / 2f
            val center = Offset(size / 2f, size / 2f)

            // Shadow effect
            drawCircle(
                color = secondary.copy(alpha = 0.1f),
                center = center,
                radius = radius + strokeWidth,
                style = Stroke(width = strokeWidth * 4)
            )

            // Background circle
            drawCircle(
                color = secondary.copy(alpha = 0.3f),
                center = center,
                radius = radius,
                style = Stroke(width = strokeWidth)
            )

            // Progress arc
            drawArc(
                color = secondary,
                startAngle = 270f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                size = Size(size, size)
            )
        }

        // Center text
        Text(
            text = value,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = textSecondary
        )
    }
}

@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = title, color = textPrimary) },
            text = { Text(description, color = textSecondary) },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = White
                    ),
                    onClick = { onConfirm(); onDismiss() }) {
                    Text("Ok", color = White)
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = White
                    ),
                    onClick = { onDismiss() }) {
                    Text("Annuler", color = White)
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false),
            containerColor = White
        )
    }
}

@Composable
fun AchievedRepetitionDialog(
    showDialog: Boolean,
    maxRepetition: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var selectedValue by remember { mutableIntStateOf(0) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Ja' atteint $selectedValue répétition", color = textPrimary) },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AndroidView(
                        factory = { context ->
                            NumberPicker(context).apply {
                                minValue = 0
                                maxValue = maxRepetition
                                setOnValueChangedListener { _, _, newVal ->
                                    selectedValue = newVal
                                }
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = White
                    ),
                    onClick = { onConfirm(selectedValue); onDismiss() }) {
                    Text("Ok", color = White)
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        contentColor = White
                    ),
                    onClick = { onDismiss() }) {
                    Text("Annuler", color = White)
                }
            },
            properties = DialogProperties(dismissOnClickOutside = false),
            containerColor = White
        )
    }
}




