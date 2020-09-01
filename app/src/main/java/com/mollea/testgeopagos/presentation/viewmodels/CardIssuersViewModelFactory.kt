package com.mollea.testgeopagos.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.presentation.viewmodels.coroutine.CoroutineContextProvider

@Suppress("UNCHECKED_CAST")
class CardIssuersViewModelFactory(
    private val repository: MercadoPagoRepository,
    private val contextProvider: CoroutineContextProvider
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = CardIssuersViewModel(repository, contextProvider) as T
}
