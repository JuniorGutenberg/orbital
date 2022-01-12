package com.orbital.machine.view.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orbital.machine.databinding.MachineMainActivityBinding
import com.orbital.machine.databinding.SearchArtistItemBinding
import com.orbital.machine.dto.ArtistaDTO
import com.orbital.machine.present.MachineMainPresent
import com.orbital.machine.view.adapter.viewholder.SearchArtistViewHolder

class SearchArtistAdapter(private var items:List<ArtistaDTO>,private var context: Activity,
                          private var binding: MachineMainActivityBinding,private var itemsSelecionados:MutableList<ArtistaDTO>,
                            private var present: MachineMainPresent):
    RecyclerView.Adapter<SearchArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {
        return SearchArtistViewHolder(SearchArtistItemBinding.inflate(LayoutInflater.from(context)),context,
            binding,itemsSelecionados,present)
    }

    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}