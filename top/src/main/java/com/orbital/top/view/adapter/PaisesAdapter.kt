package com.orbital.top.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.top.databinding.GenerosMomentosItemsBinding
import com.orbital.top.dto.PaisesDTO
import com.orbital.top.view.adapter.viewholder.PaisesViewHolder

class PaisesAdapter(private var items:List<PaisesDTO>,private var context: Context):RecyclerView.Adapter<PaisesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisesViewHolder {
        return PaisesViewHolder(GenerosMomentosItemsBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: PaisesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}