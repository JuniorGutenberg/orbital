package com.orbital.orbital_ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

class ORelativeLayout:RelativeLayout {
    constructor(context: Context?) : super(context!!) {
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!,attrs) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!,attrs,defStyleAttr) {
    }

    fun desativar(drawable:Int){
        setBackgroundResource(drawable)
        for (i in 0 until childCount){
            val child: View = getChildAt(i)
            child.isEnabled = false
        }
    }
    fun ativar(drawable:Int){
        setBackgroundResource(drawable)
        for (i in 0 until childCount){
            val child: View = getChildAt(i)
            child.isEnabled = true
        }
    }
}