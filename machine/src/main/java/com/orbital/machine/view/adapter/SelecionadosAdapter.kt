package com.orbital.machine.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.machine.databinding.SelecionadosItemBinding
import com.orbital.machine.dto.ArtistaDTO
import com.orbital.machine.view.adapter.viewholder.SelecionadosViewHolder

class SelecionadosAdapter(private var items:MutableList<ArtistaDTO>, private var context: Context):
    RecyclerView.Adapter<SelecionadosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelecionadosViewHolder {
        return SelecionadosViewHolder(SelecionadosItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: SelecionadosViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}