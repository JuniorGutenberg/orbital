package com.orbital.buscar.view.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.orbital.buscar.databinding.OnlineItemBinding
import com.orbital.buscar.dto.OnlineDTO
import com.squareup.picasso.Picasso

class OnlineViewHolder(private var binding: OnlineItemBinding, private var context: Context):
    RecyclerView.ViewHolder(binding.root) {

     fun bind(onlineDTO: OnlineDTO){
            binding.apply {
                Picasso.with(context).load(onlineDTO.thumbnail).centerCrop().fit().into(civ)
                tvTitle.text = onlineDTO.name
                tvArtista.text = onlineDTO.canal
            }
        }
}