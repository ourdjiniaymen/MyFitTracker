package fr.uge.myfittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.SeriesWithExercise

@Dao
interface ExerciseDao {
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    suspend fun insertExercise(exercise: Exercise): Long

    @Transaction
    @Query(
        "SELECT exercise.*\n" +
                "FROM exercise\n" +
                "JOIN series ON exercise.id = series.exercise_id\n" +
                "WHERE series.session_id = :sessionId;"
    )
    suspend fun getExercisesFromSessionId(sessionId: Long): List<Exercise>

    @Query("DELETE FROM sqlite_sequence WHERE name = 'exercise'")
    suspend fun resetExerciseIdSequence()

    @Query("DELETE FROM exercise")
    suspend fun deleteAllExercises()
}