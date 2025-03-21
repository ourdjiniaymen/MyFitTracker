package fr.uge.myfittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.PlanExerciseCrossRef

@Dao
interface PlanDao {
    @Insert
    suspend fun insertPlan(plan: Plan): Long

    @Insert
    suspend fun insertPlanExerciseCrossRef(exerciseCrossRef: PlanExerciseCrossRef)

   @Query("SELECT * FROM plan")
    suspend fun getAllPlans(): List<Plan>
}