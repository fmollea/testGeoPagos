package com.mollea.testgeopagos.data.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MercadoPagoRepository {
    private val publicKey = "TEST-f4485213-a7be-4975-b81e-f8d0b1218ecb";

    private val client = Retrofit.Builder()
        .baseUrl("https://api.mercadopago.com/v1/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(MercadoPagoApi::class.java)

    suspend fun getPaymentMethods() = client.getPaymentMethods(publicKey)
    suspend fun getCardIssuers(paymentMethodId: String) = client.getCardIssuers(publicKey, paymentMethodId)
    suspend fun getInstallments(paymentMethodId: String, amount: String, issuerId: String) =
        client.getInstallments(publicKey, paymentMethodId, amount, issuerId)
}