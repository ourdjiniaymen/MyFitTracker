package fr.uge.myfittracker.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class SeriesWithSteps(
    @Embedded val series: Series,
    @Relation(
        parentColumn = "id",
        entityColumn = "series_id"
    )
    val steps: List<Step>
)