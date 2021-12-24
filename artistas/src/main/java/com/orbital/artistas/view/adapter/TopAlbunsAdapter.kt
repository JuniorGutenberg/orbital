package com.orbital.artistas.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.artistas.databinding.TopAlbunsItemBinding
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.artistas.view.adapter.viewholder.TopAlbunsViewHolder

class TopAlbunsAdapter(private var items: List<AlbumDTO>,private var context: Context):RecyclerView.Adapter<TopAlbunsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAlbunsViewHolder {
        return TopAlbunsViewHolder(TopAlbunsItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: TopAlbunsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}