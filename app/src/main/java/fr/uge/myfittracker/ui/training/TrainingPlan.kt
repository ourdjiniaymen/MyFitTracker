package fr.uge.myfittracker.ui.training

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.Session
import fr.uge.myfittracker.data.model.SessionType
import fr.uge.myfittracker.data.model.SessionWithSeries
//import fr.uge.myfittracker.data.model.PlanExerciseCrossRef
import fr.uge.myfittracker.ui.theme.darkGrey
import fr.uge.myfittracker.ui.theme.darkerGrey
import fr.uge.myfittracker.ui.theme.primary
import fr.uge.myfittracker.ui.training.viewmodel.TrainingPlanViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun TrainingPlan(navController: NavController, planId:Long,  trainingPlanViewModel: TrainingPlanViewModel){
    var listExercices = remember { mutableStateListOf<Exercise>() }
    val planDetails by trainingPlanViewModel.getPlanById(planId).collectAsState(initial = null)
    val listSessions = remember { mutableStateListOf<SessionWithSeries>() }
    val sessionx = Session(planId = 1, type = SessionType.STRENGTH, repetition = 1)



    LaunchedEffect(Unit) {
        val exercises = trainingPlanViewModel.getListExercises(planId)
        listExercices.clear()
        listExercices.addAll(exercises)

        val sessions = trainingPlanViewModel.getListSessionByPlanId(planId)
        listSessions.clear()
        listSessions.addAll(sessions)
    }
    Column (modifier = Modifier
    .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally) {
        when {
            planDetails == null -> {}

            else -> {
                TopBarTrainingPlan(modifier = Modifier, planDetails!!.name, planId, trainingPlanViewModel, navController)
            }
        }

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(12.dp), verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Column {
                Text(text = "Aperçu",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(3.dp))
                Divider(Modifier.width(80.dp), color = darkGrey, thickness = 1.dp)
                Spacer(Modifier.height(20.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(12.dp)){
                    Column () {
                        Text("Notes", fontSize = 18.sp)
                        when{
                            planDetails == null->{}
                            else->{
                                Text("${planDetails!!.description}",
                                    fontSize=16.sp, color = darkerGrey)
                            }
                        }

                    }
                }
                Spacer(Modifier.height(20.dp))
                Text(text = "Sessions",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))
                LazyColumn {

                    items(listSessions.size){
                            index->
                        Session(listSessions[index].session.type.toString(), listSessions[index], navController, trainingPlanViewModel)
                        Spacer(Modifier.width(15.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarTrainingPlan(modifier: Modifier, title:String, planId: Long, trainingPlanViewModel: TrainingPlanViewModel, navController: NavController){
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(18.dp)){
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "back arrow",
                tint = Color.Black,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Text(text="${title}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold)
           Box{
               IconButton (onClick = { expanded = !expanded }) {
                   Icon(Icons.Default.MoreVert, contentDescription = "More options")
               }
               DropdownMenu(
                   expanded = expanded,
                   onDismissRequest = { expanded = false },
                   modifier = Modifier
                       .background(color = Color.White)
                       .align(Alignment.TopEnd)
               ) {
                   DropdownMenuItem(
                       text = { Text("Edit") },
                       onClick = { /* Edit */ }
                   )
                   DropdownMenuItem(
                       text = { Text("Delete", color = Color.Red) },
                       onClick = {/*Delete*/}
                   )
               }
           }

        }
    }
}

@Composable
fun Session(title: String, session:SessionWithSeries, navController: NavController, trainingPlanViewModel: TrainingPlanViewModel){
    Box(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                trainingPlanViewModel.setCurrentSession(session)
                navController.navigate("session")
            }){
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = Color.White),
            verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .width(10.dp)
                    .height(50.dp)
                    .background(primary))
            Spacer(Modifier.width(8.dp))
           Row (
               Modifier
                   .fillMaxWidth()
                   .padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween) {
               Text(text = title,
                   fontSize = 18.sp,
                   fontWeight = FontWeight.Medium
               )
               Icon(
                   imageVector = Icons.Default.KeyboardArrowRight,
                   contentDescription = "Right Arrow",
                   tint = darkerGrey,
                   modifier = Modifier.clickable {
                       trainingPlanViewModel.setCurrentSession(session)
                       navController.navigate("session") }

               )
           }
        }
    }
}

