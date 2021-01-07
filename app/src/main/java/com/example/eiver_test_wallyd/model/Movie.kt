package com.example.eiver_test_wallyd.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("poster_path")
    val posterPath: String?
) : Parcelable