package com.mollea.testgeopagos.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mollea.testgeopagos.R
import com.mollea.testgeopagos.databinding.ItemPaymentMethodRowBinding
import com.mollea.testgeopagos.domain.PaymentMethod
import com.mollea.testgeopagos.extensions.loadUrlImage
import com.mollea.testgeopagos.presentation.ui.fragments.PaymentMethodsListFragment
import com.mollea.testgeopagos.presentation.ui.fragments.PaymentMethodsListFragmentDirections

class PaymentMethodAdapter (
    private val fragment: PaymentMethodsListFragment,
    var items: List<PaymentMethod>
) : RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemPaymentMethodRowBinding>(layoutInflater,
            R.layout.item_payment_method_row, parent, false)
        return PaymentMethodViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PaymentMethodViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item
        holder.binding.ivImage.loadUrlImage(item.thumbnail, fragment.requireContext())

        holder.itemView.setOnClickListener {
            val action = PaymentMethodsListFragmentDirections.actionPaymentMethodsListFragmentToBanksListFragment(
                fragment.amount, item.id)
            fragment.findNavController().navigate(action)
        }
    }

    inner class PaymentMethodViewHolder(val binding: ItemPaymentMethodRowBinding) : RecyclerView.ViewHolder(binding.root)
}