package com.mollea.testgeopagos.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.data.repository.data.PaymentMethodsResponse
import com.mollea.testgeopagos.presentation.coroutine.TestContextProvider
import com.mollea.testgeopagos.presentation.coroutine.TestCoroutineRules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class PaymentMethodsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRules()

    @Mock
    private lateinit var repository: MercadoPagoRepository

    @Mock
    private lateinit var viewStateObserver : Observer<PaymentMethodsViewModel.PaymentMetohdViewState>

    private lateinit var viewModel: PaymentMethodsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = PaymentMethodsViewModel(
            repository,
            TestContextProvider()
        ).apply {
            getStateLiveData().observeForever(viewStateObserver)
        }
    }

    @Test
    fun `test get payment methods success`() {
        testCoroutineRule.runBlockingTest {
            val data =  Response.success(Mockito.mock(PaymentMethodsResponse::class.java))

            Mockito.`when`(repository.getPaymentMethods()).thenReturn(data)
            viewModel.getPaymentMethods()

            Mockito.verify(viewStateObserver).onChanged(PaymentMethodsViewModel.PaymentMetohdViewState.Loading)
            Mockito.verify(viewStateObserver).onChanged(PaymentMethodsViewModel.PaymentMetohdViewState.Success(
                data.body()?.toPaymentMethod() ?: emptyList()))
        }
    }

    @Test
    fun `test get payment methods fail`() {
        testCoroutineRule.runBlockingTest {
            val error = Error()

            Mockito.`when`(repository.getPaymentMethods()).thenThrow(error)
            viewModel.getPaymentMethods()

            Mockito.verify(viewStateObserver).onChanged(PaymentMethodsViewModel.PaymentMetohdViewState.Loading)
            Mockito.verify(viewStateObserver).onChanged(PaymentMethodsViewModel.PaymentMetohdViewState.Error(error))
        }
    }
}