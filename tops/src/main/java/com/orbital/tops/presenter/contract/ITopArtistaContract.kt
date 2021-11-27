package com.orbital.top.presenter.contract

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.top.dto.CallBack
import com.orbital.top.dto.TopArtistasDTO
import org.json.JSONException

interface ITopArtistaContract {
    interface ITopArtistaView{
        fun onSucess(response: List<TopArtistasDTO>)
        fun onError(error: VolleyError)
        fun onErrorJSON(error:JSONException)
    }
    interface ITopArtistaPresent{
        fun getTopArtista(activity: Activity)
    }
}