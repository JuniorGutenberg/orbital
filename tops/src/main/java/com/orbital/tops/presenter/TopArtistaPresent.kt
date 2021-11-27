package com.orbital.top.presenter

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.top.dto.CallBack
import com.orbital.top.dto.TopArtistasDTO
import com.orbital.top.model.TopArtistasPresenter
import com.orbital.top.presenter.contract.ITopArtistaContract
import com.orbital.top.service.TopArtistasService
import org.json.JSONException
import javax.inject.Inject

class TopArtistaPresent @Inject constructor(view:ITopArtistaContract.ITopArtistaView,private var topArtistasService: TopArtistasService):
    TopArtistasPresenter<ITopArtistaContract.ITopArtistaView>(view), ITopArtistaContract.ITopArtistaPresent{
    override fun getTopArtista(activity: Activity) {
        topArtistasService.getTopArtistas(activity, object : CallBack<List<TopArtistasDTO>,VolleyError>{
            override fun onSucess(response: List<TopArtistasDTO>) {
                getView()?.onSucess(response)
            }

            override fun onError(error: VolleyError) {
                getView()?.onError(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView()?.onErrorJSON(error)
            }

        })
    }
}