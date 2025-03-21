package fr.uge.myfittracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "plan_exercise",
    primaryKeys = [
        "plan_id",
        "exercise_id"
    ],
    foreignKeys = [
        ForeignKey(
            entity = Plan::class,
            parentColumns = ["id"],
            childColumns = ["plan_id"],
            onDelete = ForeignKey.CASCADE
        ),

        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlanExerciseCrossRef(
    @ColumnInfo(name = "plan_id") val planId: Long,
    @ColumnInfo("exercise_id") val exerciseId: Long
)
