package com.orbital.core.utils

import android.content.Context
import android.util.Log
import com.orbital.orbital_ui.component.OLoading

class ViewUtils {
    companion object{
        private lateinit var oLoading:OLoading
        fun loading(context: Context){
            try{
                oLoading = OLoading(context)
                oLoading.show()
            }catch (e:Exception){
                Log.e("Error Dialog Loading:",e.message.toString())
            }
        }
        fun dismissLoading(){
            try{
                oLoading.dismiss()
            }catch (e:java.lang.Exception){
                Log.e("Error Dialog Loading:",e.message.toString())

            }
        }
    }
}