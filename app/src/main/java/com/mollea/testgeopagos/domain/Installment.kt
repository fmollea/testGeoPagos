package com.mollea.testgeopagos.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Installment(
    val installments: Int,
    val installmentsAmount: Double,
    val totalAmount: Double,
    val recomendedMessage: String
) : Parcelable