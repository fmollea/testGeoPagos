package com.mollea.testgeopagos.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mollea.testgeopagos.databinding.FragmentErrorStateBinding

class ErrorStateFragment : Fragment() {

    private lateinit var binding: FragmentErrorStateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorStateBinding.inflate(inflater, container, false)
        binding.view = this
        return binding.root
    }

    fun retryAction() {
        activity?.onBackPressed()
    }

}