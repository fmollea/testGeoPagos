package com.mollea.testgeopagos.domain

data class Installment(
    val installments: Int,
    val installmentsAmount: Double,
    val totalAmount: Double,
    val recomendedMessage: String
)