package fr.uge.myfittracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.uge.myfittracker.data.local.database.DailyPerformanceDao
import fr.uge.myfittracker.data.local.database.TotalPerformanceDao
import fr.uge.myfittracker.data.local.model.DailyPerformance
import fr.uge.myfittracker.data.local.model.TotalPerformance

@Database(
    entities = [DailyPerformance::class, TotalPerformance::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyPerformanceDao(): DailyPerformanceDao
    abstract fun totalPerformanceDao(): TotalPerformanceDao
}