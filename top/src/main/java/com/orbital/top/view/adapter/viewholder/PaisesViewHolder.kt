package com.orbital.top.view.adapter.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.orbital.top.databinding.GenerosMomentosItemsBinding
import com.orbital.top.dto.PaisesDTO
import com.squareup.picasso.Picasso

class PaisesViewHolder(private var binding:GenerosMomentosItemsBinding, private var context: Context):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(paisesDTO: PaisesDTO){
        binding.apply {
            Picasso.with(context).load(paisesDTO.image).centerCrop().fit().into(iv)
        }
    }
}