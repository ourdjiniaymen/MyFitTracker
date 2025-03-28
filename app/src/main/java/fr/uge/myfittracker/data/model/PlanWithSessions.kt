package fr.uge.myfittracker.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class PlanWithSessions(
    @Embedded val plan: Plan,
    @Relation(
        parentColumn = "id",
        entityColumn = "plan_id",
        entity = Session::class
    )
    val sessions: List<SessionWithSeries>
)
