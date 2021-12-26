package com.orbital.para_voce.view.adapter.viewholder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.orbital.artistas.view.activity.ArtistasActivity
import com.orbital.para_voce.databinding.ArtistaItemBinding
import com.orbital.para_voce.dto.ArtistaDTO
import com.squareup.picasso.Picasso

class ArtistaViewHolder(private var binding: ArtistaItemBinding, private var context: Context):RecyclerView.ViewHolder(binding.root) {
    fun bind(artistaDTO: ArtistaDTO){
        binding.apply {
            tv.text = artistaDTO.nome
            Picasso.with(context).load(artistaDTO.image).centerCrop().fit().into(iv)
        }
        itemView.setOnClickListener {
            val intent = Intent(context, ArtistasActivity::class.java)
            intent.putExtra("image",artistaDTO.image)
            intent.putExtra("nome",artistaDTO.nome)
            context.startActivity(intent)
        }
    }
}