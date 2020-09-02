package com.mollea.testgeopagos.data.repository.data


import com.google.gson.annotations.SerializedName
import com.mollea.testgeopagos.domain.Installment

class InstallmentsResponse : ArrayList<InstallmentsResponseItem>() {
    fun toInstallmentList() = this[0].payerCosts.map { item ->
        Installment(
            installments = item.installments,
            installmentsAmount = item.installmentAmount,
            totalAmount = item.totalAmount,
            recomendedMessage = item.recommendedMessage
        )
    }
}