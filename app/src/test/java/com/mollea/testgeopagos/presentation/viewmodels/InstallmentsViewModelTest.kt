package com.mollea.testgeopagos.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.data.repository.data.InstallmentsResponse
import com.mollea.testgeopagos.presentation.coroutine.TestContextProvider
import com.mollea.testgeopagos.presentation.coroutine.TestCoroutineRules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class InstallmentsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRules()

    @Mock
    private lateinit var repository: MercadoPagoRepository

    @Mock
    private lateinit var viewStateObserver : Observer<InstallmentViewModel.InstallmentViewState>

    private lateinit var viewModel: InstallmentViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = InstallmentViewModel(
            repository,
            TestContextProvider()
        ).apply {
            getStateLiveData().observeForever(viewStateObserver)
        }
    }

    @Test
    fun `test get installments success`() {
        testCoroutineRule.runBlockingTest {
            val data =  Response.success(Mockito.mock(InstallmentsResponse::class.java))
            val paymentId = "paymentId"
            val issuerId = "issuerId"
            val amount = "amount"

            Mockito.`when`(repository.getInstallments(paymentId, amount, issuerId)).thenReturn(data)
            viewModel.getInstallments(paymentId, amount, issuerId)

            Mockito.verify(viewStateObserver).onChanged(InstallmentViewModel.InstallmentViewState.Loading)
            Mockito.verify(viewStateObserver).onChanged(InstallmentViewModel.InstallmentViewState.Success(
                data.body()?.toInstallmentList() ?: emptyList()))
        }
    }

    @Test
    fun `test get installments fail`() {
        testCoroutineRule.runBlockingTest {
            val error = Error()
            val paymentId = "paymentId"
            val issuerId = "issuerId"
            val amount = "amount"

            Mockito.`when`(repository.getInstallments(paymentId, amount, issuerId)).thenThrow(error)
            viewModel.getInstallments(paymentId, amount, issuerId)

            Mockito.verify(viewStateObserver).onChanged(InstallmentViewModel.InstallmentViewState.Loading)
            Mockito.verify(viewStateObserver).onChanged(InstallmentViewModel.InstallmentViewState.Error(error))
        }
    }
}