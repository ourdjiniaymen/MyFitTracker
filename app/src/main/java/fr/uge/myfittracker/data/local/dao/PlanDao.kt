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

    @Query("SELECT * FROM plan")
    suspend fun getAllPlansWithoutSession(): List<Plan>



    @Query("SELECT * FROM plan WHERE id = :planId")
    suspend fun getPlanById(planId:Long):Plan

    @Query("DELETE FROM plan")
    suspend fun deleteAllPlans()

    @Query("DELETE FROM plan WHERE id=:planId")
    fun deletePlanById(planId: Long)

    @Query("DELETE FROM sqlite_sequence WHERE name = 'plan'")
    suspend fun resetPlanIdSequence()


}