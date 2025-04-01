package fr.uge.myfittracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_performance_table")
data class DailyPerformance (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val steps: Int = 0,
    val stars: Int = 0,
    val level: Int = 0
)