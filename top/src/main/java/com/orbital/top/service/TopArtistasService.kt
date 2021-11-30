package com.orbital.top.service

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.top.dto.CallBack
import com.orbital.top.dto.PaisesDTO
import com.orbital.top.dto.TopArtistasDTO

interface TopArtistasService {
    fun getTopArtistas(activity:Activity, callback: CallBack<List<TopArtistasDTO>,VolleyError>)
    fun getGenerosMomentos(activity:Activity, callback: CallBack<List<TopArtistasDTO>,VolleyError>)
    fun getPaises(activity:Activity, callback: CallBack<List<PaisesDTO>,VolleyError>)


}