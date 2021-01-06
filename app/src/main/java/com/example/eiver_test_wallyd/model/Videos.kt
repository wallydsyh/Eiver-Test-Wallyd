package com.example.eiver_test_wallyd.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Videos(
    val key: String,
    val name: String
) : Parcelable