package com.orbital.para_voce.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.para_voce.databinding.TocadasItemBinding
import com.orbital.para_voce.dto.TocadasDTO
import com.orbital.para_voce.view.adapter.viewholder.TocadasViewHolder

class TocadasAdapter(private var items:List<TocadasDTO>, private var context: Context):
    RecyclerView.Adapter<TocadasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TocadasViewHolder {
        return TocadasViewHolder(TocadasItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: TocadasViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}