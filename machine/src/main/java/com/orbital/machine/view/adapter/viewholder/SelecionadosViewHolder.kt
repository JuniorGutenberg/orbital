package com.orbital.machine.view.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.orbital.machine.databinding.SelecionadosItemBinding
import com.orbital.machine.dto.ArtistaDTO
import com.squareup.picasso.Picasso

class SelecionadosViewHolder(private var binding: SelecionadosItemBinding,
                             private var context: Context):
    RecyclerView.ViewHolder(binding.root) {

        fun bind(artistaDTO: ArtistaDTO){
            binding.apply {
                Picasso.with(context).load(artistaDTO.image).centerCrop().fit().into(civ)
            }
        }
}