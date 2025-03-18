package fr.uge.myfittracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlanWithExercises(
    @Embedded val plan: Plan,
    @Relation(
        parentColumn = "id",
        entityColumn = "plan_id",

    )
    val exercises: List<Exercise>
)