package com.orbital.buscar.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.buscar.databinding.OnlineItemBinding
import com.orbital.buscar.dto.OnlineDTO
import com.orbital.buscar.view.adapter.viewholder.OnlineViewHolder

class OnlineAdapter(private var items: List<OnlineDTO>, private var context: Context):RecyclerView.Adapter<OnlineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlineViewHolder {

        return OnlineViewHolder(OnlineItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: OnlineViewHolder, position: Int) {
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