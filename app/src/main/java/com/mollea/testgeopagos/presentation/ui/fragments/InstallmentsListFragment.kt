package com.mollea.testgeopagos.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mollea.testgeopagos.databinding.FragmentInstallmentsListBinding
import com.mollea.testgeopagos.domain.CardIssuer
import com.mollea.testgeopagos.domain.Installment
import com.mollea.testgeopagos.domain.PaymentMethod
import com.mollea.testgeopagos.presentation.ui.adapters.InstallmentAdapter
import com.mollea.testgeopagos.presentation.viewmodels.InstallmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstallmentsListFragment : Fragment() {

    private val viewModel by activityViewModels<InstallmentViewModel>()
    private lateinit var binding: FragmentInstallmentsListBinding
    private lateinit var adapter: InstallmentAdapter
    lateinit var amount: String
    lateinit var paymentMethod: PaymentMethod
    var issuer: CardIssuer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amount = InstallmentsListFragmentArgs.fromBundle(it).amount
            paymentMethod = InstallmentsListFragmentArgs.fromBundle(it).paymentMethod
            issuer = InstallmentsListFragmentArgs.fromBundle(it).issuerCard
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstallmentsListBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        initView()
    }

    private fun initView() {
        adapter = InstallmentAdapter(this, listOf())
        binding.rvInstallmentList.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        binding.rvInstallmentList.adapter = adapter

        getInstallments()
    }

    private fun getInstallments() {
        viewModel.getInstallments(paymentMethod.id, amount, issuer?.id ?: "")
    }

    private fun observeViewModel() {
        val installmentObserver = Observer<InstallmentViewModel.InstallmentViewState> {
            when(it) {
                is InstallmentViewModel.InstallmentViewState.Loading -> showLoading()
                is InstallmentViewModel.InstallmentViewState.Success -> drawList(it.data)
                is InstallmentViewModel.InstallmentViewState.Error -> navToErrorState(it.throwable)
            }
        }

        viewModel.getStateLiveData().observe(viewLifecycleOwner, installmentObserver)
    }

    private fun navToErrorState(throwable: Throwable) {
        val action = InstallmentsListFragmentDirections.actionInstallmentsListFragmentToErrorStateFragment()
        findNavController().navigate(action)
    }

    private fun showLoading() {
        binding.rvInstallmentList.visibility = View.GONE
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun drawList(list: List<Installment>) {
        adapter.items = list
        adapter.notifyDataSetChanged()

        hideLoading()
        binding.rvInstallmentList.visibility = View.VISIBLE
    }
}