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
import fr.uge.myfittracker.data.local.dao.StepDao
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.Step

@Database(
    entities = [Step::class, Series::class, Exercise::class, Plan::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun seriesDao(): SeriesDao
    abstract fun stepDao(): StepDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
                    .also { INSTANCE = it }
            }
        }
    }
}