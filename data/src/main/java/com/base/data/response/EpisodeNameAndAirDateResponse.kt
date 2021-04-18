package com.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeNameAndAirDateResponse(
        val id: Int?,
        val name: String?,
        val air_date: String?,
        val episode: String?,
        val characters: List<String?>?,
        val url: String?,
        val created: String?
) : Parcelable