package com.orbital.orbital_ui.component

import android.app.Dialog
import android.content.Context
import com.orbital.orbital_ui.R

class OLoading:Dialog {
    constructor(context: Context):super(context){
        init()
    }
    private fun init(){
        setContentView(R.layout.loading)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}