package com.mollea.testgeopagos.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mollea.testgeopagos.databinding.FragmentAmountInputBinding
import com.mollea.testgeopagos.extensions.formatCurrency

class AmountInputFragment : Fragment() {

    private lateinit var binding: FragmentAmountInputBinding
    lateinit var amount: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        amount = ""
        binding = FragmentAmountInputBinding.inflate(inflater, container, false)
        binding.view = this
        return binding.root
    }

    fun formatAmount() {
        amount = formatCurrency(amount)
    }

    fun buttonAction() {
        if (amount.isNotEmpty()) {
            val action = AmountInputFragmentDirections.actionAmountInputFragmentToPaymentMethodsListFragment(amount)
            findNavController().navigate(action)
        } else {
            Toast.makeText(context, "Debe ingresar un monto de dinero", Toast.LENGTH_LONG).show()
        }
    }
}