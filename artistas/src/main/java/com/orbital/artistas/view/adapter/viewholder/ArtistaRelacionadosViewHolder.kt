package com.orbital.artistas.view.adapter.viewholder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.orbital.artistas.databinding.ArtistaRelacionadosItemBinding
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.artistas.view.activity.ArtistasActivity
import com.squareup.picasso.Picasso

class ArtistaRelacionadosViewHolder(private val binding: ArtistaRelacionadosItemBinding, private var context: Context):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(albumDTO: AlbumDTO){
            binding.apply {
                Picasso.with(context).load(albumDTO.image).centerCrop().fit().into(iv)
                tv.text = albumDTO.nome
            }
            itemView.setOnClickListener {
                val intent = Intent(context,ArtistasActivity::class.java)
                intent.putExtra("image",albumDTO.image)
                intent.putExtra("nome",albumDTO.nome)
                context.startActivity(intent)
            }
        }
}