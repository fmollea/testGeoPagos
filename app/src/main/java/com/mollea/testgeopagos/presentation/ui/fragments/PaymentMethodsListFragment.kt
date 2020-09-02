package com.mollea.testgeopagos.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mollea.testgeopagos.data.repository.MercadoPagoRepository
import com.mollea.testgeopagos.databinding.FragmentPaymentMethodsListBinding
import com.mollea.testgeopagos.domain.PaymentMethod
import com.mollea.testgeopagos.presentation.ui.adapters.PaymentMethodAdapter
import com.mollea.testgeopagos.presentation.viewmodels.PaymentMethodsViewModel
import com.mollea.testgeopagos.presentation.viewmodels.coroutine.CoroutineContextProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PaymentMethodsListFragment : Fragment() {

    private val viewModel by activityViewModels<PaymentMethodsViewModel>()
    private lateinit var binding: FragmentPaymentMethodsListBinding
    private lateinit var adapter: PaymentMethodAdapter
    lateinit var amount: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amount = PaymentMethodsListFragmentArgs.fromBundle(it).amount
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentMethodsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        initView()
    }

    private fun initView() {
        adapter = PaymentMethodAdapter(this, listOf())
        binding.rvPaymentMethods.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding.rvPaymentMethods.adapter = adapter

        getPaymentMethods()
    }

    private fun getPaymentMethods() {
        viewModel.getPaymentMethods()
    }

    private fun observeViewModel() {
        val paymentMethodObserver = Observer<PaymentMethodsViewModel.PaymentMetohdViewState> {
            when (it) {
                is PaymentMethodsViewModel.PaymentMetohdViewState.Loading -> showLoading()
                is PaymentMethodsViewModel.PaymentMetohdViewState.Success -> drawList(it.data)
                is PaymentMethodsViewModel.PaymentMetohdViewState.Error -> navToErrorState(it.throwable)
            }
        }
        viewModel.getStateLiveData().observe(viewLifecycleOwner, paymentMethodObserver)
    }

    private fun navToErrorState(throwable: Throwable) {
        val action = PaymentMethodsListFragmentDirections.actionPaymentMethodsListFragmentToErrorStateFragment()
        findNavController().navigate(action)
    }

    private fun showLoading() {
        binding.rvPaymentMethods.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun drawList(list: List<PaymentMethod>) {
        if (list.isEmpty()) {
            Toast.makeText(requireActivity().applicationContext, "Métodos de pago no encontrados.", Toast.LENGTH_LONG).show()
        } else {
            adapter.items = list
            adapter.notifyDataSetChanged()
        }
        hideLoading()
        binding.rvPaymentMethods.visibility = View.VISIBLE
    }
}