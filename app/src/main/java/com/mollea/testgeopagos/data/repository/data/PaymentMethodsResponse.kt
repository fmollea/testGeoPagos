package com.mollea.testgeopagos.data.repository.data

import com.google.gson.annotations.SerializedName
import com.mollea.testgeopagos.domain.PaymentMethod

class PaymentMethodsResponse : ArrayList<PaymentMethodsResponseItem>() {
    fun toPaymentMethod() = this.map { item ->
        PaymentMethod(
            id = item.id,
            name = item.name,
            thumbnail = item.secureThumbnail
        )
    }
}