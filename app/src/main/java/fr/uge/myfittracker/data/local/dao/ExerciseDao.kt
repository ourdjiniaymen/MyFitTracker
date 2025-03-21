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
    suspend fun insertExercise(exercise: Exercise): Long

    @Transaction
    @Query(
        "SELECT exercise.*\n" +
                "FROM exercise\n" +
                "JOIN plan_exercise ON exercise.id = plan_exercise.exercise_id\n" +
                "WHERE plan_exercise.plan_id = :planId;"
    )
    suspend fun getExercisesFromPlanId(planId: Long): List<Exercise>
}