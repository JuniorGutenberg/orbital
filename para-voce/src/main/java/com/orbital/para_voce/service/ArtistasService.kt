package com.orbital.para_voce.service

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.para_voce.dto.ArtistaDTO
import com.orbital.para_voce.dto.CallBack

interface ArtistasService {
    fun getArtistas(activity: Activity, callback: CallBack<List<ArtistaDTO>,VolleyError>)
}