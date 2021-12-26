package com.orbital.top.presenter.contract

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.top.dto.GenerosDTO
import org.json.JSONException

interface IGenerosContract {
    interface IGenerosView{
        fun onSucess(response: List<GenerosDTO>)
        fun onError(error:VolleyError)
        fun onErrorJSON(error:JSONException)
    }
    interface IGenerosPresenter{
        fun getPlaylist(activity: Activity,nome:String)
    }
}