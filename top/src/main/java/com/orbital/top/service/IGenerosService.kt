package com.orbital.top.service

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.core.dto.CallBack
import com.orbital.top.dto.GenerosDTO

interface IGenerosService {
    fun getPlaylist(activity: Activity,nome:String,callBack: CallBack<List<GenerosDTO>,VolleyError>)
}