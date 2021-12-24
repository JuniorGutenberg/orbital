package com.orbital.artistas.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.artistas.databinding.ArtistaRelacionadosItemBinding
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.artistas.view.adapter.viewholder.ArtistaRelacionadosViewHolder

class ArtistaRelacionadosAdapter(private var items: List<AlbumDTO>, private var context: Context):
    RecyclerView.Adapter<ArtistaRelacionadosViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ArtistaRelacionadosViewHolder {
            return ArtistaRelacionadosViewHolder(ArtistaRelacionadosItemBinding.inflate(
                LayoutInflater.from(context)),context)
        }

        override fun onBindViewHolder(holder: ArtistaRelacionadosViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int {
           return items.size
        }
}