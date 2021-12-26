package com.orbital.top.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.top.databinding.GenerosMainItemBinding
import com.orbital.top.dto.GenerosDTO
import com.orbital.top.view.adapter.viewholder.GenerosMainViewHolder

class GenerosMainAdapter(private var items:List<GenerosDTO>, private var context: Context):RecyclerView.Adapter<GenerosMainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenerosMainViewHolder {
        return GenerosMainViewHolder(GenerosMainItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: GenerosMainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}