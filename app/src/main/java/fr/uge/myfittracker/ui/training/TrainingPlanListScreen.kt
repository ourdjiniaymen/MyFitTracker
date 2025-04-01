package fr.uge.myfittracker.ui.training

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.uge.myfittracker.R
import fr.uge.myfittracker.data.model.Exercise
//import fr.uge.myfittracker.data.model.ExerciseType
import fr.uge.myfittracker.data.model.Plan
//import fr.uge.myfittracker.data.model.PlanExerciseCrossRef
import fr.uge.myfittracker.data.repository.Repository
//import fr.uge.myfittracker.ui.home.getCurrentDate
import fr.uge.myfittracker.ui.navigation.BottomNavItem
import fr.uge.myfittracker.ui.training.viewmodel.TrainingPlanViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun TrainingPlanListScreen(navController: NavController, trainingPlanListModel:TrainingPlanViewModel = viewModel()){

    val listPlans by trainingPlanListModel.plans.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBarPlan(Modifier, navController = navController)
        Spacer(Modifier.height(20.dp))
        LazyColumn {
            items(listPlans.size){
                index->
                Plan(Modifier, listPlans[index], trainingPlanListModel, navController)
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun TopBarPlan(modifier: Modifier, navController: NavController){

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
                modifier = Modifier.clickable { navController.navigate(BottomNavItem.Train.navRoute) }
            )
            Text(text="Plans Entrainements",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.add),
                    contentDescription = "new",
                    tint = Color(0XFF05CD9E)
                )
            }
        }
    }
}


@Composable
fun Plan(modifier: Modifier, plan:Plan, trainingPlanListModel: TrainingPlanViewModel, navController: NavController){
    Box(
        modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(12.dp).clickable {
                trainingPlanListModel.setCurrentPlan(plan)
                navController.navigate("Plan")}){
        Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Row(verticalAlignment = Alignment.CenterVertically){
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_training),
                    contentDescription = "training",
                    tint = Color.Black
                )
                Spacer(modifier.width(10.dp))
                Column {
                    Text(text = plan.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Created on ${plan.date}",
                            fontSize = 12.sp,
                            color = Color(0XFF9E9B9B))
                        Spacer(modifier.width(20.dp))
                        if (plan.started){
                            Box(modifier.border(1.dp, Color(0XFF7CA4F7), RoundedCornerShape(20.dp))
                                .background(color = Color(0XA8E3EAF8))
                                .width(90.dp)
                            ){
                                Text(text = "Started",
                                    fontSize = 10.sp,
                                    color = Color(0XFF0547CD),
                                    modifier = Modifier.align(Alignment.Center)
                                    )
                            }
                        }
                    }
                }
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Right Arrow",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    trainingPlanListModel.setCurrentPlan(plan)
                    navController.navigate("Plan")  }

            )
        }
    }
}
