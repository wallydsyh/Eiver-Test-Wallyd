package com.example.eiver_test_wallyd.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Videos(
    val id: String,
    val key: String
) : Parcelable