package com.mollea.testgeopagos.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mollea.testgeopagos.R
import com.mollea.testgeopagos.databinding.ItemInstallmentRowBinding
import com.mollea.testgeopagos.domain.Installment
import com.mollea.testgeopagos.presentation.ui.fragments.InstallmentsListFragment
import com.mollea.testgeopagos.presentation.ui.fragments.InstallmentsListFragmentDirections

class InstallmentAdapter(
    private val fragment: InstallmentsListFragment,
    var items: List<Installment>
) : RecyclerView.Adapter<InstallmentAdapter.InstallmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemInstallmentRowBinding>(layoutInflater,
            R.layout.item_installment_row, parent, false)
        return InstallmentViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: InstallmentViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item

        holder.itemView.setOnClickListener {
            val action = InstallmentsListFragmentDirections.actionInstallmentsListFragmentToSuccessFragment(
                fragment.amount, fragment.paymentMethod, fragment.issuer, item)
            fragment.findNavController().navigate(action)
        }
    }

    inner class InstallmentViewHolder(val binding: ItemInstallmentRowBinding) : RecyclerView.ViewHolder(binding.root)
}