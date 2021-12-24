package com.orbital.artistas.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.artistas.databinding.TopTrackItemBinding
import com.orbital.artistas.dto.TopTrackDTO
import com.orbital.artistas.view.adapter.viewholder.TopTrackViewHolder

class TopTrackAdapter(private var items: List<TopTrackDTO>, private var context: Context):
    RecyclerView.Adapter<TopTrackViewHolder>() {

    private val NUMBER_ITEMS = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopTrackViewHolder {

        return TopTrackViewHolder(TopTrackItemBinding.inflate(LayoutInflater.from(context)),context)
    }

    override fun onBindViewHolder(holder: TopTrackViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return if(items.size>=NUMBER_ITEMS){
            NUMBER_ITEMS
        }else{
            items.size
        }
    }
}