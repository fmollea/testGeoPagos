package com.mollea.testgeopagos.data.repository

import com.mollea.testgeopagos.data.repository.data.InstallmentsResponse
import com.mollea.testgeopagos.data.repository.data.CardIssuersResponse
import com.mollea.testgeopagos.data.repository.data.PaymentMethodsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MercadoPagoApi {

    @GET("payment_methods")
    suspend fun getPaymentMethods(
        @Query("public_key") publicKey: String
    ): Response<PaymentMethodsResponse>

    @GET("card_issuers")
    suspend fun getCardIssuers(
        @Query("public_key") publicKey: String,
        @Query("payment_method_id") paymentMethodId: String
    ): Response<CardIssuersResponse>

    @GET("installments")
    suspend fun getInstallments(
        @Query("public_key") publicKey: String,
        @Query("payment_method_id") paymentMethodId: String,
        @Query("amount") amount: String,
        @Query("issuer_id") issuerId: String
    ): Response<InstallmentsResponse>
}