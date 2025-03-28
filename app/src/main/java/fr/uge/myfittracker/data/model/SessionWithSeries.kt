package fr.uge.myfittracker.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class SessionWithSeries(
    @Embedded val session: Session,
    @Relation(
        parentColumn = "id",
        entityColumn = "session_id",
        entity = Series::class
    )
    val series: List<SeriesWithExercise>
)

