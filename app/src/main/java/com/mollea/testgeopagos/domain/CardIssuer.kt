package com.mollea.testgeopagos.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardIssuer(
    val id: String,
    val name: String,
    val thumbnail: String
) : Parcelable