package fr.uge.myfittracker.ui.training

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import fr.uge.myfittracker.R
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.ui.navigation.BottomNavItem
import fr.uge.myfittracker.ui.training.viewmodel.TrainingPlanViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(navController: NavController, trainingPlanViewModel: TrainingPlanViewModel){
    //var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var selectedDateMillis by remember { mutableStateOf(trainingPlanViewModel.selectedDate) }
    val allPlans by trainingPlanViewModel.plans.collectAsState()

    LaunchedEffect(selectedDateMillis) {
        trainingPlanViewModel.selectedDate = selectedDateMillis
    }
    val filteredPlans = allPlans.filter {
        convertDateToMillis(it.date) == selectedDateMillis && it.started == true
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBarHistory(Modifier, navController = navController, trainingPlanViewModel)
        Spacer(Modifier.height(20.dp))
        DatePickerDocked{
            selectedMillis -> selectedDateMillis = selectedMillis
        }
        Spacer(Modifier.height(20.dp))
        if (filteredPlans.isNotEmpty()){
            LazyColumn {
            items(filteredPlans.size){
                    index->
                Plan(Modifier,index+1, filteredPlans[index].name, filteredPlans[index].date, filteredPlans[index].started, navController)
                Spacer(Modifier.height(10.dp))
            }
        }
        }else{
            Text("Aucun plan pour la date selectionnée.")
        }
    }
}


@Composable
fun TopBarHistory(modifier: Modifier, navController: NavController, trainingPlanViewModel: TrainingPlanViewModel){

    Box(
        modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(18.dp)){
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "back arrow",
                tint = Color.Black,
                modifier = Modifier.clickable { navController.navigate(BottomNavItem.Train.navRoute)
                    trainingPlanViewModel.selectedDate = null
                }
            )
            Spacer(modifier=Modifier.width(130.dp))
            Text(text="Historique",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(onDateSelected: (Long) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("jj/mm/aaaa", color = Color.DarkGray) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false
                        )
                        Button(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    onDateSelected(millis)
                                }
                                showDatePicker = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Confirm", fontWeight = FontWeight.SemiBold, color = Color.White)
                        }
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
fun convertDateToMillis(dateString: String): Long {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        formatter.parse(dateString)?.time ?: 0L
    } catch (e: Exception) {
        0L
    }
}