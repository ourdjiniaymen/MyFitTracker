package fr.uge.myfittracker.ui.training

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.uge.myfittracker.R
import androidx.compose.ui.tooling.preview.Preview as Preview1

@Composable
fun TrainingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        //verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(Modifier)
        Box(Modifier.fillMaxWidth()){
            Text(text = "MES ENTRAINEMENTS",
                color = Color.Gray,
                fontSize = 18.sp,
                modifier = Modifier.padding(17.dp).align(Alignment.TopStart))
        }
        TrainingPlan(Modifier, navController =navController)
        Spacer(Modifier.height(10.dp))
        History(Modifier, navController = navController)
    }
}

@Composable
fun TopBar(modifier: Modifier){
    Box(modifier.fillMaxWidth().background(color = Color.White).padding(18.dp)){
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(text="Entrainements",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold)
        }
    }
}


@Composable
fun TrainingPlan(modifier:Modifier, navController: NavController){
    Box(modifier.fillMaxWidth().background(color = Color.White).padding(12.dp).clickable { navController.navigate("PlanEntrainements")  }){
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Row(){
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "calendar",
                    tint = Color.Black
                )
                Spacer(modifier.width(10.dp))
                Text(text = "Plans d'entrainements",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Right Arrow",
                tint = Color.Black,
                modifier = Modifier.clickable { navController.navigate("PlanEntrainements")  }

            )
        }
    }
}


@Composable
fun History(modifier:Modifier, navController: NavController){
    Box(modifier.fillMaxWidth().background(color = Color.White).padding(12.dp).clickable { navController.navigate("History") }){
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Row(){
                Icon(
                    imageVector = ImageVector.vectorResource( id = R.drawable.history),
                    contentDescription = "calendar",
                    tint = Color.Black
                )
                Spacer(modifier.width(10.dp))
                Text(text = "Historique",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Right Arrow",
                tint = Color.Black,
                modifier = Modifier.clickable { navController.navigate("History") }
            )
        }
    }
}

