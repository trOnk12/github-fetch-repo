package com.example.brightinventions.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
    val id: Int,
    val commits: List<Commit>
) : Parcelable
