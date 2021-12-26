package com.orbital.playlist.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.playlist.databinding.PlaylistItemBinding
import com.orbital.playlist.dto.OnlineDTO
import com.orbital.playlist.view.adapter.viewholder.PlaylistViewHolder

class PlaylistAdapter(private var items:List<OnlineDTO>, private var context: Context):RecyclerView.Adapter<PlaylistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(PlaylistItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}