package com.mollea.testgeopagos.di

import com.google.gson.GsonBuilder
import com.mollea.testgeopagos.data.repository.MercadoPagoApi
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.PublicKey

@Module
@InstallIn(ApplicationComponent::class)
class DataRepositoryModule {

    @Provides
    fun provideClient() = Retrofit.Builder()
        .baseUrl("https://api.mercadopago.com/v1/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(MercadoPagoApi::class.java)

    @Provides
    fun provideMercadoPagoRepository(client: MercadoPagoApi) =
        MercadoPagoRepository(client)
}