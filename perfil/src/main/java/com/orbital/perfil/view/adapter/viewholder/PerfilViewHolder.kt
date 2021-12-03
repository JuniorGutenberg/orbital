package com.orbital.perfil.view.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.orbital.perfil.R
import com.orbital.perfil.databinding.PerfilItemBinding

class PerfilViewHolder(private var binding: PerfilItemBinding):RecyclerView.ViewHolder(binding.root) {

    fun bind(nome:String){
        binding.apply {
            if(nome == "SLEEP_TIMER"){
                tv.text = "Sleep Timer"
                iv.setImageResource(R.drawable.ic_sleeptimer)
            }else if(nome == "CONFIGURACOES"){
                tv.text = "Configurações"
                iv.setImageResource(R.drawable.ic_config)
            }else if(nome == "SOBRE"){
                tv.text = "Sobre"
                iv.setImageResource(R.drawable.ic_sobre)
            }else if(nome == "TWITTER"){
                tv.text = "Twitter"
                iv.setImageResource(R.drawable.ic_twitter)
            }else if(nome == "INSTAGRAM"){
                tv.text = "Instagram"
                iv.setImageResource(R.drawable.ic_instagram)
            }
        }
    }
}