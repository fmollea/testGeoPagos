package com.mollea.testgeopagos.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.data.repository.data.CardIssuersResponse
import com.mollea.testgeopagos.presentation.coroutine.TestContextProvider
import com.mollea.testgeopagos.presentation.coroutine.TestCoroutineRules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class CardIssuersViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRules()

    @Mock
    private lateinit var repository: MercadoPagoRepository

    @Mock
    private lateinit var viewStateObserver : Observer<CardIssuersViewModel.CardIssuersViewState>

    private lateinit var viewModel: CardIssuersViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = CardIssuersViewModel(
            repository,
            TestContextProvider()
        ).apply {
            getStateLiveData().observeForever(viewStateObserver)
        }
    }

    @Test
    fun `test get card issuers success`() {
        testCoroutineRule.runBlockingTest {
            val data =  Response.success(Mockito.mock(CardIssuersResponse::class.java))
            val paymentId = "paymentId"

            Mockito.`when`(repository.getCardIssuers(paymentId)).thenReturn(data)
            viewModel.getCardIssuers(paymentId)

            Mockito.verify(viewStateObserver).onChanged(CardIssuersViewModel.CardIssuersViewState.Loading)
            Mockito.verify(viewStateObserver).onChanged(CardIssuersViewModel.CardIssuersViewState.Success(
                data.body()?.toCardIssuerList() ?: emptyList()))
        }
    }

    @Test
    fun `test get card issuers fail`() {
        testCoroutineRule.runBlockingTest {
            val error = Error()
            val paymentId = "paymentId"

            Mockito.`when`(repository.getCardIssuers(paymentId)).thenThrow(error)
            viewModel.getCardIssuers(paymentId)

            Mockito.verify(viewStateObserver).onChanged(CardIssuersViewModel.CardIssuersViewState.Loading)
            Mockito.verify(viewStateObserver).onChanged(CardIssuersViewModel.CardIssuersViewState.Error(error))
        }
    }
}