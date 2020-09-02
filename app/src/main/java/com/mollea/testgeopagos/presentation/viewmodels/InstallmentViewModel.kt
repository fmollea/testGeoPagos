package com.mollea.testgeopagos.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.domain.Installment
import com.mollea.testgeopagos.presentation.viewmodels.coroutine.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InstallmentViewModel @ViewModelInject constructor(
    private val repository: MercadoPagoRepository,
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        stateLiveData.value = InstallmentViewState.Error(exception)
    }

    private val stateLiveData = MutableLiveData<InstallmentViewState>()
    fun getStateLiveData(): LiveData<InstallmentViewState> = stateLiveData

    fun getInstallments(paymentMethodId: String, amount: String, issuerId: String) {
        stateLiveData.value = InstallmentViewState.Loading
        viewModelScope.launch(handler) {
            val data = withContext(contextProvider.IO) {
                repository.getInstallments(paymentMethodId, amount, issuerId)
            }
            stateLiveData.value = InstallmentViewState.Success(
                data.body()?.toInstallmentList() ?: emptyList()
            )
        }
    }

    sealed class InstallmentViewState {
        object Loading : InstallmentViewState()
        data class Error(val throwable: Throwable) : InstallmentViewState()
        data class Success(val data: List<Installment>) : InstallmentViewState()
    }
}