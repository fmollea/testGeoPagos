package com.mollea.testgeopagos.data.repository.data


import com.google.gson.annotations.SerializedName

data class PayerCost(
    @SerializedName("discount_rate")
    val discountRate: Int,
    @SerializedName("installment_amount")
    val installmentAmount: Double,
    @SerializedName("installment_rate")
    val installmentRate: Double,
    @SerializedName("installment_rate_collector")
    val installmentRateCollector: List<String>,
    @SerializedName("installments")
    val installments: Int,
    @SerializedName("labels")
    val labels: List<String>,
    @SerializedName("max_allowed_amount")
    val maxAllowedAmount: Int,
    @SerializedName("min_allowed_amount")
    val minAllowedAmount: Int,
    @SerializedName("payment_method_option_id")
    val paymentMethodOptionId: String,
    @SerializedName("recommended_message")
    val recommendedMessage: String,
    @SerializedName("reimbursement_rate")
    val reimbursementRate: Any,
    @SerializedName("total_amount")
    val totalAmount: Double
)