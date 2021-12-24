package com.orbital.artistas.view.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.orbital.artistas.databinding.TopAlbunsItemBinding
import com.orbital.artistas.dto.AlbumDTO
import com.squareup.picasso.Picasso

class TopAlbunsViewHolder(private var binding: TopAlbunsItemBinding, private var context: Context):RecyclerView.ViewHolder(binding.root) {
    fun bind(topAlbumDTO: AlbumDTO){
        binding.apply {
            Picasso.with(context).load(topAlbumDTO.image).centerCrop().fit().into(riv)
            tv.text = topAlbumDTO.nome
        }
    }
}