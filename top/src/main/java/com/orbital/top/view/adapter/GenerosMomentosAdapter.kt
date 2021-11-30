package com.orbital.top.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.top.databinding.GenerosMomentosItemsBinding
import com.orbital.top.dto.TopArtistasDTO
import com.orbital.top.view.adapter.viewholder.GenerosMomentosViewHolder

class GenerosMomentosAdapter(private var items: List<TopArtistasDTO>, private var context: Context):
    RecyclerView.Adapter<GenerosMomentosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenerosMomentosViewHolder {
       return GenerosMomentosViewHolder(GenerosMomentosItemsBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: GenerosMomentosViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
       return items.size
    }
}