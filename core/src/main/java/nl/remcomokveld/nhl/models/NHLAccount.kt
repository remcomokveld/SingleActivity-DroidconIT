package nl.remcomokveld.nhl.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NHLAccount constructor(
    val email: String,
    val favoriteTeam: Team
) : Parcelable
