package fr.uge.myfittracker.data.local.dao

import androidx.annotation.TransitionRes
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.PlanWithExercises

@Dao
interface PlanDao {
    @Insert
    suspend fun insertPlan(plan: Plan)

    @Transaction
    @Query("SELECT * FROM plan")
    suspend fun getAllPlans(): List<PlanWithExercises>
}