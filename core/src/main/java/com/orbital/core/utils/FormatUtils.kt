package com.orbital.core.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat

class FormatUtils {
    companion object{
        fun changeColorDrawable(context: Context, drawable: Drawable, color: Int):Drawable{
            drawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(context,color),
                BlendModeCompat.SRC_ATOP)

            return drawable
        }
    }
}