package com.orbital.biblioteca.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.biblioteca.databinding.BibliotecaItemBinding
import com.orbital.biblioteca.view.adapter.viewholder.BibliotecaViewHolder

class BibliotecaAdapter(private var items:List<String>,private var context: Context):RecyclerView.Adapter<BibliotecaViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibliotecaViewHolder {
        return BibliotecaViewHolder(BibliotecaItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: BibliotecaViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}