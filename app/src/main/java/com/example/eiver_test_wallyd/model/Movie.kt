package com.example.eiver_test_wallyd.model

import com.google.gson.annotations.SerializedName

class Movie(
   val popularity: Double,
   @SerializedName("vote_count") val voteCount: Int,
   val video: Boolean,
   @SerializedName("poster_path") val posterPath: String?,
   val id: Long,
   val adult: Boolean,
   @SerializedName("backdrop_path") val backdropPath: String?,
   @SerializedName("original_language") val originalLanguage: String,
   @SerializedName("original_title") val originalTitle: String,
   val title: String,
   @SerializedName("vote_average") val voteAverage: Double,
   val overview: String,
   @SerializedName("release_date") val releaseDate: String?
)