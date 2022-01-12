package com.orbital.machine.view.adapter.viewholder

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.orbital.core.utils.ViewUtils
import com.orbital.machine.R
import com.orbital.machine.databinding.MachineMainActivityBinding
import com.orbital.machine.databinding.SearchArtistItemBinding
import com.orbital.machine.dto.ArtistaDTO
import com.orbital.machine.present.MachineMainPresent
import com.orbital.machine.view.adapter.SelecionadosAdapter
import com.squareup.picasso.Picasso

class SearchArtistViewHolder(private var binding: SearchArtistItemBinding,private var context: Activity,
                             private var bindingMain: MachineMainActivityBinding, private var items: MutableList<ArtistaDTO>,
private var present: MachineMainPresent):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(artistaDTO: ArtistaDTO){
        binding.apply {
            Picasso.with(context).load(artistaDTO.image).centerCrop().fit().into(civ)
            tv.text = artistaDTO.nome
        }
        itemView.setOnClickListener {
            val itemsComp:MutableList<String> = arrayListOf()


            for (i in 0 until items.size){
                itemsComp.add(items[i].image)
            }

            if(!itemsComp.contains(artistaDTO.image)){
               add(artistaDTO)
            }else{
                remove(artistaDTO)
            }
        }
        bindingMain.rlCheck.setOnClickListener {
            if(items.size>0) {
                ViewUtils.loading(context)
                present.buildPlaylist(listNomes(), context)
            }
        }
    }
    private fun listNomes():List<String>{
        val itemsList:MutableList<String> = arrayListOf()
        for (i in 0 until items.size){
            itemsList.add(items[i].nome)
        }
        return itemsList
    }
    private fun add(artistaDTO: ArtistaDTO){
        items.add(artistaDTO)
        bindingMain.rlCheck.ativar(R.drawable.back_circular_laranja)
        if(bindingMain.rvSelecionados.adapter!=null){
            bindingMain.rvSelecionados.adapter!!.notifyDataSetChanged()
        }else{
            bindingMain.rvSelecionados.adapter = SelecionadosAdapter(items, context)
        }
    }
    private fun remove(artistaDTO: ArtistaDTO){
        items.remove(artistaDTO)
        if(items.size<=0){
            bindingMain.rlCheck.desativar(R.drawable.back_circular_desativado)
        }
        if(bindingMain.rvSelecionados.adapter!=null){
            bindingMain.rvSelecionados.adapter!!.notifyDataSetChanged()
        }else{
            bindingMain.rvSelecionados.adapter = SelecionadosAdapter(items,context)
        }
    }
}