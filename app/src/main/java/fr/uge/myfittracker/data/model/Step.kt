package fr.uge.myfittracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "step",
    foreignKeys = [
        ForeignKey(
            entity = Series::class,
            parentColumns = ["id"],
            childColumns = ["series_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]

)
data class Step(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "series_id")
    val seriesId: Long,
    val title: String,
    val description: String,
    val type: StepType,
    val duration: Int
);