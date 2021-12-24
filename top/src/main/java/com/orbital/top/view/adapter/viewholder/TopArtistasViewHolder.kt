package com.orbital.top.view.adapter.viewholder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.orbital.artistas.view.activity.ArtistasActivity
import com.orbital.core.utils.StringUtils
import com.orbital.top.databinding.TopArtistasItemBinding
import com.orbital.top.dto.TopArtistasDTO
import com.squareup.picasso.Picasso

class TopArtistasViewHolder(private var binding: TopArtistasItemBinding,private var context: Context)
    :RecyclerView.ViewHolder(binding.root) {

    fun bind(topArtistasDTO: TopArtistasDTO){
        binding.apply {
            tv.text = StringUtils.removePlusToSpace(topArtistasDTO.nome)
            Picasso.with(context).load(topArtistasDTO.image).centerCrop().fit().into(iv)
        }

        itemView.setOnClickListener {
            val intent = Intent(context,ArtistasActivity::class.java)
            intent.putExtra("image",topArtistasDTO.image)
            intent.putExtra("nome",topArtistasDTO.nome)
            context.startActivity(intent)
        }
    }
}