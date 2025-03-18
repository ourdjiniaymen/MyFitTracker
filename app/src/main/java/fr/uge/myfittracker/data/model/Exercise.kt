package fr.uge.myfittracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise",
    foreignKeys = [
        ForeignKey(
            entity = Plan::class,
            parentColumns = ["id"],
            childColumns = ["plan_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "plan_id") val planId: Long,
    val name: String,
    val description: String,
    val type: ExerciseType,
    val repetition: Int

)