package fr.uge.myfittracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_performance_table")
data class DailyPerformance (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val steps: Int = 0,
    val validatedSteps: Int = 0,
    val distance: Float = 0f,
    val calories: Float = 0f,
    val stars: Int = 0
)