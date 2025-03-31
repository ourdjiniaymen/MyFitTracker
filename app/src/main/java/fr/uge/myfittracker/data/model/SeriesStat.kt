package fr.uge.myfittracker.data.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class SeriesStat(
    val series : @RawValue SeriesWithExercise,
    val achievedScore : Int,
    val isCompleted : Boolean
):Parcelable