package com.orbital.perfil.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.perfil.databinding.PerfilItemBinding
import com.orbital.perfil.view.adapter.viewholder.PerfilViewHolder

class PerfilAdapter(private var items:List<String>,private var context: Context): RecyclerView.Adapter<PerfilViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilViewHolder {
        return PerfilViewHolder(PerfilItemBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onBindViewHolder(holder: PerfilViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}