package com.base.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterResponse(
        val info: Info,
        val results: List<Results>
) : Parcelable

@Parcelize
data class Info(
        val count: Int?,
        val pages: Int?,
        val next: String?,
        val prev: String?
) : Parcelable

@Parcelize
data class Results(
        val id: Int?,
        val name: String?,
        val status: String?,
        val species: String?,
        val type: String?,
        val gender: String?,
        val image: String?,
        val url: String?,
        val created: String,
        val episode: List<String>,
        val location: Location?,
        val origin: Origin?
) : Parcelable

@Parcelize
data class Location(
        val name: String?,
        val url: String?
) : Parcelable

@Parcelize
data class Origin(
        val name: String?,
        val url: String?
) : Parcelable