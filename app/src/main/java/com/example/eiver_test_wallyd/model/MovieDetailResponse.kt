package com.example.eiver_test_wallyd.model

import com.example.eiver_test_wallyd.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String?
) {

}