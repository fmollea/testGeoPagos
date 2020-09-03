package com.mollea.testgeopagos.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mollea.testgeopagos.databinding.FragmentSuccessBinding
import com.mollea.testgeopagos.domain.CardIssuer
import com.mollea.testgeopagos.domain.Installment
import com.mollea.testgeopagos.domain.PaymentMethod
import com.mollea.testgeopagos.extensions.loadUrlImage

class SuccessFragment : Fragment() {

    lateinit var binding: FragmentSuccessBinding
    lateinit var amount: String
    lateinit var paymentMethod: PaymentMethod
    var issuer: CardIssuer? = null
    lateinit var installment: Installment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amount = SuccessFragmentArgs.fromBundle(it).amount
            paymentMethod = SuccessFragmentArgs.fromBundle(it).paymentMethod
            issuer = SuccessFragmentArgs.fromBundle(it).issuerCard
            installment = SuccessFragmentArgs.fromBundle(it).installment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessBinding.inflate(
            inflater, container, false
        )
        binding.view = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadImages()
    }

    private fun loadImages() {
        binding.ivImagePayment.loadUrlImage(paymentMethod.thumbnail, requireContext())
        issuer?.let {
            binding.ivImageIssuer.loadUrlImage(it.thumbnail, requireContext())
        }
    }

    fun navToAmountInputFragment() {
        val action = SuccessFragmentDirections.actionSuccessFragmentToAmountInputFragment()
        findNavController().navigate(action)
    }

    companion object {
        const val DEFAULT = "default"
    }
}