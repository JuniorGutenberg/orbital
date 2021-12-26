package com.orbital.playlist.view.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.orbital.playlist.databinding.PlaylistItemBinding
import com.orbital.playlist.dto.OnlineDTO
import com.squareup.picasso.Picasso

class PlaylistViewHolder(private var binding: PlaylistItemBinding,private var context: Context):RecyclerView.ViewHolder(binding.root) {
    fun bind(onlineDTO: OnlineDTO){
        binding.apply {
            Picasso.with(context).load(onlineDTO.thumbnail).centerCrop().fit().into(civ)
            tvTitle.text = onlineDTO.name
            tvArtista.text = onlineDTO.canal
        }
    }
}