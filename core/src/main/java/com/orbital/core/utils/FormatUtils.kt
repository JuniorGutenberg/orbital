package com.orbital.core.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import java.text.DecimalFormat

class FormatUtils {
    companion object{
        fun changeColorDrawable(context: Context, drawable: Drawable, color: Int):Drawable{
            drawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(context,color),
                BlendModeCompat.SRC_ATOP)

            return drawable
        }
        fun formatToNomenclature(listerners:String):String{

            val number: Double = listerners.toDouble()
            if (number < 1000) {
                return listerners
            }
            if (number > 1000 && number < 1000000) {
                val decimalFormat = DecimalFormat("0.00K")
                return decimalFormat.format(number / 1000)
            }
            if (number > 1000000 && number < 1000000000) {
                val decimalFormat = DecimalFormat("0.00M")
                return decimalFormat.format(number / 1000000)
            }
            if (number > 1000000000) {
                val decimalFormat = DecimalFormat("0.00B")
                return decimalFormat.format(number / 1000000000)
            }
            return ""
        }
    }
}