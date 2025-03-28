package fr.uge.myfittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.PlanWithSessions

@Dao
interface PlanDao {
    @Insert
    suspend fun insertPlan(plan: Plan): Long


    @Query("SELECT * FROM plan")
    suspend fun getAllPlans(): List<PlanWithSessions>
}