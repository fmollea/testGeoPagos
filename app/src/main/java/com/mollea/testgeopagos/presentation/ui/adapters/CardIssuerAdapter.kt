package com.mollea.testgeopagos.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mollea.testgeopagos.R
import com.mollea.testgeopagos.databinding.ItemCardIssuerRowBinding
import com.mollea.testgeopagos.domain.CardIssuer
import com.mollea.testgeopagos.extensions.loadUrlImage
import com.mollea.testgeopagos.presentation.ui.fragments.CardIssuersFragment
import com.mollea.testgeopagos.presentation.ui.fragments.CardIssuersFragmentDirections

class CardIssuerAdapter (
    private val fragment: CardIssuersFragment,
    var items: List<CardIssuer>
) : RecyclerView.Adapter<CardIssuerAdapter.CardIssuerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardIssuerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCardIssuerRowBinding>(layoutInflater,
            R.layout.item_card_issuer_row, parent, false)
        return CardIssuerViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CardIssuerViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item
        holder.binding.ivImage.loadUrlImage(item.thumbnail, fragment.requireContext())

        holder.itemView.setOnClickListener {
            val action = CardIssuersFragmentDirections.actionBanksListFragmentToInstallmentsListFragment(
                fragment.amount, fragment.paymentMethod, item
            )
            fragment.findNavController().navigate(action)
        }
    }

    inner class CardIssuerViewHolder(val binding: ItemCardIssuerRowBinding) : RecyclerView.ViewHolder(binding.root)
}