package com.example.brightinventions.feature.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Commit(
    val author: Author,
    val detail: Detail
) : Parcelable

@Parcelize
data class Author(
    val name: String
) : Parcelable

@Parcelize
data class Detail(
    val message: String,
    val SHA: String
) : Parcelable