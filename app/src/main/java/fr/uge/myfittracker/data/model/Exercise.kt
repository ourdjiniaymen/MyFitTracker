package fr.uge.myfittracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise",
)

data class Exercise(
     /*
     Represents the fundamental entity in the database,
     not dependent on any other table.
      */
     @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String?,
)