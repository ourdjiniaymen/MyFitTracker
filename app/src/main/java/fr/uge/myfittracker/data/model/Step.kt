package fr.uge.myfittracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "step",
)
data class Step(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String?,
    val type: StepType,
    val durationBased: Boolean = true,
    val duration: Int,
    val count: Int?
);