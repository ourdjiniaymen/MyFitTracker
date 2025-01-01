package fr.uge.myfittracker.data.local.database

import androidx.room.*
import fr.uge.myfittracker.data.local.model.TotalPerformance

@Dao
interface TotalPerformanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTotalPerformance(performance: TotalPerformance)

    @Query("SELECT * FROM total_performance_table WHERE id = 1")
    suspend fun getTotalPerformance(): TotalPerformance?

    @Update
    suspend fun updateTotalPerformance(performance: TotalPerformance)
}