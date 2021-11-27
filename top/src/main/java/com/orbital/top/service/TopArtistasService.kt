package com.orbital.top.service

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.top.dto.CallBack
import com.orbital.top.dto.TopArtistasDTO

interface TopArtistasService {
    fun getTopArtistas(activity:Activity, callback: CallBack<List<TopArtistasDTO>,VolleyError>)
}