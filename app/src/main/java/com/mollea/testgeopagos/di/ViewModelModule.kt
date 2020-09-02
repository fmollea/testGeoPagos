package com.mollea.testgeopagos.di

import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.presentation.viewmodels.CardIssuersViewModel
import com.mollea.testgeopagos.presentation.viewmodels.InstallmentViewModel
import com.mollea.testgeopagos.presentation.viewmodels.PaymentMethodsViewModel
import com.mollea.testgeopagos.presentation.viewmodels.coroutine.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class ViewModelModule {

    @Provides
    fun provideCoroutineContextProvider() = CoroutineContextProvider()

    @Provides
    fun providePatmentMethodViewModel(repository: MercadoPagoRepository, contextProvider: CoroutineContextProvider) =
        PaymentMethodsViewModel(repository, contextProvider)

    @Provides
    fun provideCardIssuerViewModel(repository: MercadoPagoRepository, contextProvider: CoroutineContextProvider) =
        CardIssuersViewModel(repository, contextProvider)

    @Provides
    fun provideInstallmentViewModel(repository: MercadoPagoRepository, contextProvider: CoroutineContextProvider) =
        InstallmentViewModel(repository, contextProvider)
}