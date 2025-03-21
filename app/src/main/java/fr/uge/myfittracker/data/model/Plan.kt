package fr.uge.myfittracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan")
data class Plan(
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name : String,
    val description : String?
)