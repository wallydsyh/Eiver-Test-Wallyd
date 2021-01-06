package com.example.eiver_test_wallyd.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val title: String,
    val overview: String,
    val video: Boolean,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?
)  {

}