package com.orbital.core.factory

import android.content.Context
import com.orbital.core.data.BaseSharedPreferences


class OrbitalSharedPreferences(private var nome:String,private var context: Context): BaseSharedPreferences() {
    companion object{
        var CADASTRO: String = "CADASTRO_CODE"
    }

    fun setCadastroStatus(status: Boolean){
        this.get()?.edit()?.putBoolean(CADASTRO,status)?.apply()
    }
    fun getCadastroStatus(): Boolean? {
        return this.get()?.getBoolean(CADASTRO,false)
    }
}