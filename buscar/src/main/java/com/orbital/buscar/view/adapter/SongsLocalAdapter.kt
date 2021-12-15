package com.orbital.buscar.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.buscar.databinding.SongsLocalItemBinding
import com.orbital.buscar.dto.SongsLocalDTO
import com.orbital.buscar.view.adapter.viewholder.SongsLocalViewHolder

class SongsLocalAdapter(private var items: List<SongsLocalDTO>, private var context: Context):RecyclerView.Adapter<SongsLocalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsLocalViewHolder {
        return SongsLocalViewHolder(SongsLocalItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: SongsLocalViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return if(items.size>=6){
            6
        }else{
            items.size
        }
    }
}