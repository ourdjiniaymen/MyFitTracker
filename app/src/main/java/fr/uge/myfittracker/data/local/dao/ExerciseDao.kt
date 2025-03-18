package fr.uge.myfittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.ExerciseWithSeries

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Transaction
    @Query("SELECT * FROM Exercise WHERE plan_id = :planId")
    suspend fun getExercisesForPlan(planId: Long): List<ExerciseWithSeries>
}