package fr.uge.myfittracker.data.model

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ExerciseWithSeries(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "id",
        entityColumn = "exercise_id",
    )
    val series: List<SeriesWithStep>
)

