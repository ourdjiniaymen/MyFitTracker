package fr.uge.myfittracker.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class SeriesWithStep(
    @Embedded val series: Series,
    @Relation(
        parentColumn = "step_id",
        entityColumn = "id"
    )
    val step: Step,
)