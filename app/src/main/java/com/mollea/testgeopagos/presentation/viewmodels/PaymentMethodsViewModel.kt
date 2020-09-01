package com.mollea.testgeopagos.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.domain.PaymentMethod
import com.mollea.testgeopagos.presentation.viewmodels.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentMethodsViewModel(
    private val repository: MercadoPagoRepository,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = PaymentMetohdViewState.Error(exception)
    }

    private val stateLiveData = MutableLiveData<PaymentMetohdViewState>()
    fun getStateLiveData(): LiveData<PaymentMetohdViewState> = stateLiveData

    fun getPaymentMethods() {
        stateLiveData.value = PaymentMetohdViewState.Loading
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                repository.getPaymentMethods()
            }
            stateLiveData.value = PaymentMetohdViewState.Success(
                data.body()?.toPaymentMethod() ?: emptyList()
            )
        }
    }

    sealed class PaymentMetohdViewState {
        object Loading : PaymentMetohdViewState()
        data class Error(val throwable: Throwable) : PaymentMetohdViewState()
        data class Success(val data: List<PaymentMethod>) : PaymentMetohdViewState()
    }
}