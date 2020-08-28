package com.mollea.testgeopagos.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.domain.PaymentMethod
import com.mollea.testgeopagos.presentation.viewmodels.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler

class CardIssuersViewModel(
    private val repository: MercadoPagoRepository,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = PaymentMethodsViewModel.PaymentMetohdViewState.Error(exception)
    }

    private val stateLiveData = MutableLiveData<PaymentMethodsViewModel.PaymentMetohdViewState>()
    fun getStateLiveData(): LiveData<PaymentMethodsViewModel.PaymentMetohdViewState> = stateLiveData

    sealed class CardIssuersViewState {
        object Loading : CardIssuersViewState()
        data class Error(val throwable: Throwable) : CardIssuersViewState()
        data class SuccessSong(val data: List<PaymentMethod>) : CardIssuersViewState()
    }
}