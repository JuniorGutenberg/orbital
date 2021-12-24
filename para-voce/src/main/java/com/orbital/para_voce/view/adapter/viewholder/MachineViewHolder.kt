package com.orbital.para_voce.view.adapter.viewholder

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView
import com.orbital.core.utils.FormatUtils
import com.orbital.para_voce.R
import com.orbital.para_voce.databinding.MachineItemBinding

class MachineViewHolder(private var binding: MachineItemBinding, private var context: Context):RecyclerView.ViewHolder(binding.root) {
    fun bind(nome: String){
        binding.apply {
            tv.text = nome
            val drawable = AppCompatResources.getDrawable(context, R.drawable.ic_playlist)
            iv.setImageDrawable(drawable?.let {
                FormatUtils.changeColorDrawable(context,
                    it,R.color.black)
            })
        }
    }
}