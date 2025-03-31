package fr.uge.myfittracker.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class SessionStat(
    val session : @RawValue Session,
    val seriesStat : List<SeriesStat>
):Parcelable