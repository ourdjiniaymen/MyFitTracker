package fr.uge.myfittracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "total_performance_table")
data class TotalPerformance(
    @PrimaryKey val id: Int = 1,
    val totalSteps: Int = 0,
    val totalStars: Int = 0
)
