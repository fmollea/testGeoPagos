package com.mollea.testgeopagos.data.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MercadoPagoRepository @Inject constructor(
    private val client: MercadoPagoApi
) {
    private val publicKey = "TEST-f4485213-a7be-4975-b81e-f8d0b1218ecb"

    suspend fun getPaymentMethods() = client.getPaymentMethods(publicKey)
    suspend fun getCardIssuers(paymentMethodId: String) = client.getCardIssuers(publicKey, paymentMethodId)
    suspend fun getInstallments(paymentMethodId: String, amount: String, issuerId: String) =
        client.getInstallments(publicKey, paymentMethodId, amount, issuerId)
}