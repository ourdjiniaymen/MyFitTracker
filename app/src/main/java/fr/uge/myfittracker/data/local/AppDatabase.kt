package fr.uge.myfittracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.uge.myfittracker.data.local.converters.Converters
import fr.uge.myfittracker.data.local.dao.ExerciseDao
import fr.uge.myfittracker.data.local.dao.PlanDao
import fr.uge.myfittracker.data.local.dao.SeriesDao
import fr.uge.myfittracker.data.local.dao.SessionDao
import fr.uge.myfittracker.data.local.database.DailyPerformanceDao
import fr.uge.myfittracker.data.local.model.DailyPerformance
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.Session

@Database(
    entities = [Exercise::class, Series::class, Session::class, Plan::class, DailyPerformance::class],
    version = 6
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyPerformanceDao(): DailyPerformanceDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun seriesDao(): SeriesDao
    abstract fun sessionDao(): SessionDao
    abstract fun planDao(): PlanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, // Use application context
                    AppDatabase::class.java,
                    "app_db"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}