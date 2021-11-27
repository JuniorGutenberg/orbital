package com.orbital.top.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.top.databinding.TopArtistasItemBinding
import com.orbital.top.dto.TopArtistasDTO
import com.orbital.top.view.adapter.viewholder.TopArtistasViewHolder

class TopArtistaAdapter(var items: List<TopArtistasDTO>, var context:Context):
    RecyclerView.Adapter<TopArtistasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopArtistasViewHolder {
        return TopArtistasViewHolder(TopArtistasItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: TopArtistasViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
       return items.size
    }
}