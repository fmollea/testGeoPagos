package com.mollea.testgeopagos.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.domain.CardIssuer
import com.mollea.testgeopagos.presentation.viewmodels.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardIssuersViewModel @ViewModelInject constructor(
    private val repository: MercadoPagoRepository,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = CardIssuersViewState.Error(exception)
    }

    private val stateLiveData = MutableLiveData<CardIssuersViewState>()
    fun getStateLiveData(): LiveData<CardIssuersViewState> = stateLiveData

    fun getCardIssuers(paymentMethodId: String) {
        stateLiveData.value = CardIssuersViewState.Loading
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                repository.getCardIssuers(paymentMethodId)
            }
            stateLiveData.value = CardIssuersViewState.Success(
                data.body()?.toCardIssuerList() ?: emptyList()
            )
        }
    }

    sealed class CardIssuersViewState {
        object Loading : CardIssuersViewState()
        data class Error(val throwable: Throwable) : CardIssuersViewState()
        data class Success(val data: List<CardIssuer>) : CardIssuersViewState()
    }
}