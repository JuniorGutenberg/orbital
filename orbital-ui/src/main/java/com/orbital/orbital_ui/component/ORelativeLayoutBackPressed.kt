package com.orbital.orbital_ui.component

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class ORelativeLayoutBackPressed: RelativeLayout {
    private var activity:Activity?=null
    constructor(context: Context?) : super(context!!) {
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!,attrs) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!,attrs,defStyleAttr) {
    }
    constructor(activity: Activity):super(activity){
        this.activity = activity
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        activity?.onBackPressed()
        activity?.finish()
    }
}