package fr.uge.myfittracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "series",
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        ),

        ForeignKey(
            entity = Step::class,
            parentColumns = ["id"],
            childColumns = ["step_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Series(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "exercise_id") val exerciseId: Long,
    @ColumnInfo(name = "step_id") val stepId: Long,
    @ColumnInfo(name = "step_repetition") val stepRepetition: Int,
    @ColumnInfo(name = "repetition_in_exercise") val repetitionInExercise: Int
);