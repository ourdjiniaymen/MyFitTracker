package fr.uge.myfittracker.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class SeriesWithExercise(
    @Embedded val series: Series,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "id"
    )
    val exercise: Exercise,
)