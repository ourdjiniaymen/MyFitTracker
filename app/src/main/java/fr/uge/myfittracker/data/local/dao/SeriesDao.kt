package fr.uge.myfittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithStep

@Dao
interface SeriesDao {
    @Insert
    suspend fun insertSeries(series: Series) : Long

    @Transaction
    @Query("SELECT * FROM series WHERE exercise_id = :exerciseId")
    suspend fun getListSeriesFromExerciseId(exerciseId : Long) : List<SeriesWithStep>

}
