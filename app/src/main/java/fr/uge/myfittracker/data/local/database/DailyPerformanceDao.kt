package fr.uge.myfittracker.data.local.database

import androidx.room.*
import fr.uge.myfittracker.data.local.model.DailyPerformance

@Dao
interface DailyPerformanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyPerformance(performance: DailyPerformance)

    @Update
    suspend fun updateDailyPerformance(performance: DailyPerformance)

    @Query("SELECT * FROM daily_performance_table WHERE date = :date")
    suspend fun getDailyPerformanceByDate(date: String): DailyPerformance?

    @Query("SELECT * FROM daily_performance_table")
    suspend fun getAllDailyPerformances(): List<DailyPerformance>

    @Query("SELECT SUM(steps) FROM daily_performance_table")
    suspend fun getTotalSteps(): Int?

    @Query("SELECT SUM(stars) FROM daily_performance_table")
    suspend fun getTotalStars(): Int?
}