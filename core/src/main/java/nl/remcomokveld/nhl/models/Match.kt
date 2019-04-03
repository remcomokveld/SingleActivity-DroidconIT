package nl.remcomokveld.nhl.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    val homeTeam: Team,
    val awayTeam: Team,
    val homeScore: Int,
    val awayScore: Int
) : Parcelable
