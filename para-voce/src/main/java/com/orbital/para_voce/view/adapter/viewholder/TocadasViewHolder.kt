package com.orbital.para_voce.view.adapter.viewholder

import android.content.Context
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView
import com.orbital.core.utils.StringUtils
import com.orbital.para_voce.R
import com.orbital.para_voce.databinding.TocadasItemBinding
import com.orbital.para_voce.dto.TocadasDTO
import com.squareup.picasso.Picasso

class TocadasViewHolder(private var binding: TocadasItemBinding, private var context: Context):
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tocadasDTO: TocadasDTO){
        binding.apply {
            if(tocadasDTO.categoria == "CATEGORY_ALBUM"){
                riv.visibility = View.VISIBLE
                civ.visibility = View.GONE
                rlMachine.visibility = View.GONE
                frameItemsPlayListMain.visibility = View.GONE

                Picasso.with(context).load(tocadasDTO.imageAlbum).centerCrop().fit().into(riv)
                tv.text = tocadasDTO.nomeAlbum
            }
            if(tocadasDTO.categoria == "CATEGORY_MACHINE"){
                riv.visibility = View.GONE
                civ.visibility = View.GONE
                rlMachine.visibility = View.VISIBLE
                frameItemsPlayListMain.visibility = View.GONE

                val drawable = AppCompatResources.getDrawable(context, R.drawable.ic_playlist)
                drawable?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    ContextCompat.getColor(context, R.color.black),
                    BlendModeCompat.SRC_ATOP)
                ivMachine.setImageDrawable(drawable)

                tv.text = tocadasDTO.nomeMachine
            }
            if(tocadasDTO.categoria == "CATEGORY_ARTISTA"){
                riv.visibility = View.GONE
                civ.visibility = View.VISIBLE
                rlMachine.visibility = View.GONE
                frameItemsPlayListMain.visibility = View.GONE

                Picasso.with(context).load(tocadasDTO.imageArtista).centerCrop().fit().into(civ)

                tv.text = StringUtils.removePlusToSpace(tocadasDTO.nomeArtista)
            }
            if(tocadasDTO.categoria == "CATEGORY_PLAYLIST"){
                riv.visibility = View.VISIBLE
                civ.visibility = View.GONE
                rlMachine.visibility = View.GONE
                frameItemsPlayListMain.visibility = View.GONE

                Picasso.with(context).load(tocadasDTO.imagePlaylist).centerCrop().fit().into(riv)

                tv.text = tocadasDTO.nomePlaylist
            }
        }
    }
}