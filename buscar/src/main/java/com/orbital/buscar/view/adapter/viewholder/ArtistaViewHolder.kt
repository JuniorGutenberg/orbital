package com.orbital.buscar.view.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.orbital.buscar.databinding.ArtistaItemBinding
import com.orbital.buscar.dto.ArtistaDTO
import com.squareup.picasso.Picasso

class ArtistaViewHolder(private var binding: ArtistaItemBinding, private var context: Context):RecyclerView.ViewHolder(binding.root) {
    fun bind(artistaDTO: ArtistaDTO){
        binding.apply {
            tv.text = artistaDTO.nome
            Picasso.with(context).load(artistaDTO.image).centerCrop().fit().into(iv)
        }
    }
}