package com.orbital.top.presenter.contract

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.top.dto.CallBack
import com.orbital.top.dto.PaisesDTO
import com.orbital.top.dto.TopArtistasDTO
import org.json.JSONException

interface ITopArtistaContract {
    interface ITopArtistaView{
        fun onSucessTop(response: List<TopArtistasDTO>)
        fun onErrorTop(error: VolleyError)
        fun onErrorJSONTop(error:JSONException)

        fun onSucessGeneros(response: List<TopArtistasDTO>)
        fun onErrorGeneros(error: VolleyError)
        fun onErrorJSONGeneros(error:JSONException)

        fun onSucessPaises(response: List<PaisesDTO>)
        fun onErrorPaises(error: VolleyError)
        fun onErrorJSONPaises(error:JSONException)
    }
    interface ITopArtistaPresent{
        fun getTopArtista(activity: Activity)
        fun getGenerosMomentos(activity: Activity)
        fun getPaises(activity: Activity)


    }
}