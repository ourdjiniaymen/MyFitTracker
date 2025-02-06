package fr.uge.myfittracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.uge.myfittracker.data.local.database.DailyPerformanceDao
import fr.uge.myfittracker.data.local.model.DailyPerformance

@Database(
    entities = [DailyPerformance::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyPerformanceDao(): DailyPerformanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "performance_database"
                ).fallbackToDestructiveMigration() // Supprime la DB en cas de changement de version
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}