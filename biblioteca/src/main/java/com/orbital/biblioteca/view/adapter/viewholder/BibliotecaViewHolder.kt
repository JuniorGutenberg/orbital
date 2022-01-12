package com.orbital.biblioteca.view.adapter.viewholder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.orbital.biblioteca.R
import com.orbital.biblioteca.databinding.BibliotecaItemBinding
import com.orbital.machine.view.activity.MachineMainActivity

class BibliotecaViewHolder(private var binding:BibliotecaItemBinding,private var context: Context): RecyclerView.ViewHolder(binding.root) {
    /**
     * ARRUMAR STRINGS
     * */
    fun bind(nome: String){
        binding.apply {
            if(nome == "FAIXAS"){
                tv.text = "Faixas"
                fb.setImageResource(R.drawable.ic_faixas)
            }else if(nome == "PLAYLIST"){
                tv.text = "Playlist"
                fb.setImageResource(R.drawable.ic_playlist)

            }else if(nome == "PASTA"){
                tv.text = "Pasta"
                fb.setImageResource(R.drawable.ic_pasta)
            }else if(nome == "MACHINE"){
                tv.text = "Orbital Machine"
                fb.setImageResource(R.drawable.ic_robo)
                fb.setOnClickListener {
                    val intent = Intent(context,MachineMainActivity::class.java)
                    context.startActivity(intent)
                }
                itemView.setOnClickListener {
                    val intent = Intent(context,MachineMainActivity::class.java)
                    context.startActivity(intent)
                }
            }else if(nome == "ARTISTAS"){
                tv.text = "Artistas"
                fb.setImageResource(R.drawable.ic_artistas)
            }else if(nome == "HISTORICO"){
                tv.text = "Histórico"
                fb.setImageResource(R.drawable.ic_historico)
            }else if(nome == "FAVORITOS"){
                tv.text = "Favoritos"
                fb.setImageResource(R.drawable.ic_heart)
            }
        }
    }
}