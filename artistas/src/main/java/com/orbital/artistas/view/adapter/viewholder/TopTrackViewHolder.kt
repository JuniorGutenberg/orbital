package com.orbital.artistas.view.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.orbital.artistas.databinding.TopTrackItemBinding
import com.orbital.artistas.dto.TopTrackDTO
import com.squareup.picasso.Picasso

class TopTrackViewHolder (private var binding: TopTrackItemBinding, private var context: Context):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(topTrackDTO: TopTrackDTO){
        binding.apply {
            Picasso.with(context).load(topTrackDTO.thumbnail).centerCrop().fit().into(civ)
            tvTitle.text = topTrackDTO.name
            tvArtista.text = topTrackDTO.canal
        }
    }
}