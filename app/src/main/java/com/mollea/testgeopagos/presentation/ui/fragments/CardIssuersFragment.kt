package com.mollea.testgeopagos.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mollea.testgeopagos.R
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.databinding.FragmentCardIssuersBinding
import com.mollea.testgeopagos.domain.CardIssuer
import com.mollea.testgeopagos.domain.PaymentMethod
import com.mollea.testgeopagos.presentation.ui.activities.MainActivity
import com.mollea.testgeopagos.presentation.ui.adapters.CardIssuerAdapter
import com.mollea.testgeopagos.presentation.viewmodels.CardIssuersViewModel
import com.mollea.testgeopagos.presentation.viewmodels.CardIssuersViewModelFactory
import com.mollea.testgeopagos.presentation.viewmodels.PaymentMethodsViewModel
import com.mollea.testgeopagos.presentation.viewmodels.coroutine.CoroutineContextProvider

class CardIssuersFragment : Fragment() {

    private lateinit var viewModel: CardIssuersViewModel
    private lateinit var binding: FragmentCardIssuersBinding
    private lateinit var adapter: CardIssuerAdapter
    lateinit var amount: String
    lateinit var paymentMethodId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amount = CardIssuersFragmentArgs.fromBundle(it).amount
            paymentMethodId = CardIssuersFragmentArgs.fromBundle(it).paymentMethodId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardIssuersBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val repository = MercadoPagoRepository()
        val contextProvider = CoroutineContextProvider()
        val factory = CardIssuersViewModelFactory(repository, contextProvider)

        viewModel = ViewModelProviders.of(this, factory).get(CardIssuersViewModel::class.java)
        observeViewModel()
        initView()
    }

    private fun initView() {
        (requireActivity() as MainActivity).title = "Lista de bancos"
        adapter = CardIssuerAdapter(this, listOf())
        binding.rvBankList.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding.rvBankList.adapter = adapter

        getCardIssuers()
    }

    private fun getCardIssuers() {
        viewModel.getCardIssuers(paymentMethodId)
    }

    private fun observeViewModel() {
        val cardIssuerObserver = Observer<CardIssuersViewModel.CardIssuersViewState> {
            when (it) {
                is CardIssuersViewModel.CardIssuersViewState.Loading -> showLoading()
                is CardIssuersViewModel.CardIssuersViewState.Success -> drawList(it.data)
                is CardIssuersViewModel.CardIssuersViewState.Error -> navToErrorState(it.throwable)
            }
        }
        viewModel.getStateLiveData().observe(viewLifecycleOwner, cardIssuerObserver)
    }

    private fun navToErrorState(throwable: Throwable) {
        val action = CardIssuersFragmentDirections.actionBanksListFragmentToErrorStateFragment()
        findNavController().navigate(action)
    }

    private fun navToInstallmentsWithOutCardIssuer() {
        val action = CardIssuersFragmentDirections.actionBanksListFragmentToInstallmentsListFragment()
        val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.paymentMethodsListFragment, false     ).build()
        findNavController().navigate(action, navOptions)
    }

    private fun showLoading() {
        binding.rvBankList.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun drawList(list: List<CardIssuer>) {
        if (list.isEmpty()) {
            navToInstallmentsWithOutCardIssuer()
        } else {
            adapter.items = list
            adapter.notifyDataSetChanged()
        }
        hideLoading()
        binding.rvBankList.visibility = View.VISIBLE
    }
}