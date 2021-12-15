package com.orbital.buscar.view.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.orbital.buscar.R
import com.orbital.buscar.databinding.SongsLocalItemBinding
import com.orbital.buscar.dto.SongsLocalDTO

class SongsLocalViewHolder(private var binding: SongsLocalItemBinding, context: Context):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(songsLocalDTO: SongsLocalDTO){
        binding.apply {
            tvTitle.text = songsLocalDTO.titulo
            if(songsLocalDTO.artista == "<unknown>"){
                tvArtista.text = "Desconhecido"
            }else {
                tvArtista.text = songsLocalDTO.artista
            }
            iv.setImageResource(R.drawable.ic_song)
        }
    }
}