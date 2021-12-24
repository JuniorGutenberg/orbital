package com.orbital.top.presenter

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.top.dto.BannersDTO
import com.orbital.top.dto.CallBack
import com.orbital.top.dto.PaisesDTO
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
                getView()?.onSucessTop(response)
            }

            override fun onError(error: VolleyError) {
                getView()?.onErrorTop(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView()?.onErrorJSONTop(error)
            }

        })
    }

    override fun getGenerosMomentos(activity: Activity) {
        topArtistasService.getGenerosMomentos(activity, object : CallBack<List<TopArtistasDTO>,VolleyError>{
            override fun onSucess(response: List<TopArtistasDTO>) {
                getView()?.onSucessGeneros(response)
            }

            override fun onError(error: VolleyError) {
                getView()?.onErrorGeneros(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView()?.onErrorJSONGeneros(error)
            }

        })
    }

    override fun getPaises(activity: Activity) {
        topArtistasService.getPaises(activity, object: CallBack<List<PaisesDTO>,VolleyError>{
            override fun onSucess(response: List<PaisesDTO>) {
                getView()?.onSucessPaises(response)
            }

            override fun onError(error: VolleyError) {
                getView()?.onErrorPaises(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView()?.onErrorJSONPaises(error)
            }

        })
    }

    override fun getBanners(activity: Activity) {
        topArtistasService.getBanners(activity, object: CallBack<List<BannersDTO>,VolleyError>{
            override fun onSucess(response: List<BannersDTO>) {
                getView()?.onSucessBanners(response)
            }

            override fun onError(error: VolleyError) {
                getView()?.onErrorBanners(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView()?.onErrorJSONBanners(error)
            }
        })
    }
}