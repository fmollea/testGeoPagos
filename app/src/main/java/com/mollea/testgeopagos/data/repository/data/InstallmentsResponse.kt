package com.mollea.testgeopagos.data.repository.data


import com.google.gson.annotations.SerializedName
import com.mollea.testgeopagos.domain.Installment

data class InstallmentsResponse(
    @SerializedName("agreements")
    val agreements: Any,
    @SerializedName("issuer")
    val issuer: Issuer,
    @SerializedName("merchant_account_id")
    val merchantAccountId: Any,
    @SerializedName("payer_costs")
    val payerCosts: List<PayerCost>,
    @SerializedName("payment_method_id")
    val paymentMethodId: String,
    @SerializedName("payment_type_id")
    val paymentTypeId: String,
    @SerializedName("processing_mode")
    val processingMode: String
) {
    fun toInstallmentList() = payerCosts.map { item ->
        Installment(
            installments = item.installments,
            installmentsAmount = item.installmentAmount,
            totalAmount = item.totalAmount,
            recomendedMessage = item.recommendedMessage
        )
    }
}