package com.example.eiver_test_wallyd.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class VideosResponse(
    val id: Int,
    val results: List<Videos>
)  {

}