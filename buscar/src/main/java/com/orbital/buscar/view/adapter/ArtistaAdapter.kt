package com.orbital.buscar.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.buscar.databinding.ArtistaItemBinding
import com.orbital.buscar.dto.ArtistaDTO
import com.orbital.buscar.view.adapter.viewholder.ArtistaViewHolder

class ArtistaAdapter(private var items:List<ArtistaDTO>, private var context: Context):RecyclerView.Adapter<ArtistaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistaViewHolder {
        return ArtistaViewHolder(ArtistaItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: ArtistaViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}