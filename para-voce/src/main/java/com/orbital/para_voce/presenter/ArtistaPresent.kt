package com.orbital.para_voce.presenter

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.para_voce.dto.ArtistaDTO
import com.orbital.para_voce.dto.CallBack
import com.orbital.para_voce.model.ArtistaPresenter
import com.orbital.para_voce.presenter.contract.IArtistaContract
import com.orbital.para_voce.service.ArtistasService
import org.json.JSONException
import javax.inject.Inject

class ArtistaPresent @Inject constructor(view: IArtistaContract.ArtistaView,private var artistasService: ArtistasService):
    ArtistaPresenter<IArtistaContract.ArtistaView>(view), IArtistaContract.AritstaPresenter{
    override fun getArtistas(activity: Activity) {
        artistasService.getArtistas(activity, object : CallBack<List<ArtistaDTO>,VolleyError>{
            override fun onSucess(response: List<ArtistaDTO>) {
                getView().onSucess(response)
            }

            override fun onError(error: VolleyError) {
                getView().onError()
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onError()
            }
        })
    }
}