package fr.uge.myfittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithSteps

@Dao
interface SeriesDao {
    @Insert
    suspend fun insertSeries(series: Series)

    @Transaction
    @Query("SELECT * FROM Series WHERE exercise_id = :exerciseId")
    fun getSeriesWithSteps(exerciseId: Long): List<SeriesWithSteps>

}