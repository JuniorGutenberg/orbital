package com.orbital.buscar.present

import android.app.Activity
import com.orbital.buscar.dto.*
import com.orbital.buscar.model.SongsLocalPresenter
import com.orbital.buscar.present.contract.ISongsLocalContract
import com.orbital.buscar.service.SongsLocalService
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

class SongsLocalPresent @Inject constructor(view: ISongsLocalContract.ISongsLocalView,private var songsLocalService: SongsLocalService):
    SongsLocalPresenter<ISongsLocalContract.ISongsLocalView>(view), ISongsLocalContract.ISongsLocalPresenter {
    override fun getSongsLocal(key: String, activity: Activity) {
        songsLocalService.getSongs(key,activity, object : CallbackSongsLocal<List<SongsLocalDTO>>{
            override fun onSucess(response: List<SongsLocalDTO>) {
                getView().onSucess(response)
            }

            override fun onError() {
                getView().onError()
            }
        })
    }

    override fun getOnline(key: String, activity: Activity) {
        songsLocalService.getSongsOnline(key, activity, object: CallbackOnline<List<OnlineDTO>,IOException>{
            override fun onSucess(response: List<OnlineDTO>) {
                getView().onSucessOnline(response)
                response[0].ytId?.let { getRelacionados(it, activity) }
            }

            override fun onError(error: IOException) {
                getView().onErrorOnline(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSONOnline(error)
            }
        })
    }

    override fun getArtista(key: String, activity: Activity) {
        songsLocalService.getArtista(key,activity, object : CallbackOnline<List<ArtistaDTO>,IOException>{
            override fun onSucess(response: List<ArtistaDTO>) {
                getView().onSucessArtista(response)
            }

            override fun onError(error: IOException) {
                getView().onErrorArtista(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSONArtista(error)
            }

        })
    }

    private fun getRelacionados(key: String, activity: Activity){
        songsLocalService.getSongsRelacionados(key,activity, object :CallbackOnline<List<OnlineDTO>,IOException>{
            override fun onSucess(response: List<OnlineDTO>) {
                getView().onSucessRelacionados(response)
            }

            override fun onError(error: IOException) {
                getView().onErrorRelacionados(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSONRelacionados(error)
            }
        })
    }
}