package com.orbital.top.view.adapter.viewholder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.orbital.playlist.view.activity.PlaylistActivity
import com.orbital.top.databinding.GenerosMainItemBinding
import com.orbital.top.dto.GenerosDTO
import com.squareup.picasso.Picasso

class GenerosMainViewHolder(private var binding: GenerosMainItemBinding,private var context: Context):RecyclerView.ViewHolder(binding.root) {
    fun bind(generosDTO: GenerosDTO){
        binding.apply {
            Picasso.with(context).load(generosDTO.image).centerCrop().fit().into(riv)
            tv.text = generosDTO.name
        }
        itemView.setOnClickListener {
            val intent = Intent(context, PlaylistActivity::class.java)
            intent.putExtra("nome",generosDTO.name)
            intent.putExtra("image",generosDTO.image)
            intent.putExtra("autor",generosDTO.autor)
            intent.putExtra("idYoutube",generosDTO.idPlaylist)
            intent.putExtra("twitter",generosDTO.twitter)
            intent.putExtra("instagram",generosDTO.instagram)
            context.startActivity(intent)
        }
    }
}