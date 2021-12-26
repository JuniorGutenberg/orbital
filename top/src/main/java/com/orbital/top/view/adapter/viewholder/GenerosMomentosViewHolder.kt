package com.orbital.top.view.adapter.viewholder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.orbital.top.databinding.GenerosMomentosItemsBinding
import com.orbital.top.dto.TopArtistasDTO
import com.orbital.top.view.activity.GenerosMainActivity
import com.squareup.picasso.Picasso

class GenerosMomentosViewHolder(private var binding: GenerosMomentosItemsBinding, private var context: Context):
    RecyclerView.ViewHolder(binding.root) {
    fun bind(topArtistasDTO: TopArtistasDTO){
        binding.apply {
            Picasso.with(context).load(topArtistasDTO.image).centerCrop().fit().into(iv)
        }
        itemView.setOnClickListener {
            val intent = Intent(context,GenerosMainActivity::class.java)
            intent.putExtra("nome",topArtistasDTO.nome.replaceFirstChar { it.uppercase() })
            context.startActivity(intent)
        }
    }
}