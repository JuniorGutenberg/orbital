package com.orbital.top.view.adapter.viewholder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.orbital.playlist.view.activity.PlaylistActivity
import com.orbital.top.databinding.GenerosMomentosItemsBinding
import com.orbital.top.dto.PaisesDTO
import com.squareup.picasso.Picasso

class PaisesViewHolder(private var binding:GenerosMomentosItemsBinding, private var context: Context):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(paisesDTO: PaisesDTO){
        binding.apply {
            Picasso.with(context).load(paisesDTO.image).centerCrop().fit().into(iv)
        }
        itemView.setOnClickListener {
            val intent = Intent(context,PlaylistActivity::class.java)
            intent.putExtra("nome",paisesDTO.nome)
            intent.putExtra("image",paisesDTO.image)
            intent.putExtra("autor",paisesDTO.autor)
            intent.putExtra("idYoutube",paisesDTO.idYoutube)
            intent.putExtra("twitter",paisesDTO.twitter)
            intent.putExtra("instagram",paisesDTO.instagram)
            context.startActivity(intent)
        }
    }
}