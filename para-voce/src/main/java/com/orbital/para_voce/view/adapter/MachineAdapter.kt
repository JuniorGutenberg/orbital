package com.orbital.para_voce.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.para_voce.databinding.MachineItemBinding
import com.orbital.para_voce.view.adapter.viewholder.MachineViewHolder

class MachineAdapter(private var items: List<String>, private var context: Context):RecyclerView.Adapter<MachineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MachineViewHolder {
        return MachineViewHolder(MachineItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: MachineViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}