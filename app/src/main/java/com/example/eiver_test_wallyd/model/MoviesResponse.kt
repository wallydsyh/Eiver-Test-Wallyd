package com.example.eiver_test_wallyd.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("total_pages") val total: Int = 0,
    val page: Int = 0,
    val results: List<Movie>
) {

}