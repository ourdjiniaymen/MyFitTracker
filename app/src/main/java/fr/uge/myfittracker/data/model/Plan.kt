package fr.uge.myfittracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan")
data class Plan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name : String,
)