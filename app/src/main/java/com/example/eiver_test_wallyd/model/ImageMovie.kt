package com.example.eiver_test_wallyd.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageMovie(val url: String): Parcelable {
    companion object {
        private const val PATH = "https://image.tmdb.org/t/p"
    }

    val small: Uri = Uri.parse("$PATH/w92/$url")

    val medium: Uri = Uri.parse("$PATH/w185/$url")

    val large: Uri = Uri.parse("$PATH/w342/$url")

    val original: Uri = Uri.parse("$PATH/original/$url")
}

