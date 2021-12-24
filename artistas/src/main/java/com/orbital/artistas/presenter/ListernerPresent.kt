package com.orbital.artistas.presenter

import android.app.Activity
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.artistas.dto.TopTrackDTO
import com.orbital.artistas.model.ListernerPresenter
import com.orbital.artistas.presenter.contract.IListernerContract
import com.orbital.artistas.service.ListernerService
import com.orbital.core.dto.CallBack
import com.orbital.core.utils.FormatUtils
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

class ListernerPresent @Inject constructor(view: IListernerContract.IListernerView, private  var listernerService: ListernerService)
    :ListernerPresenter<IListernerContract.IListernerView>(view),IListernerContract.IListernerPresenter{
    override fun getListerner(activity: Activity, artista:String) {
        listernerService.getListerners(activity,artista ,object :CallBack<String,IOException>{

            override fun onSucess(response: String) {
                getView().onSucess(FormatUtils.formatToNomenclature(response))
            }

            override fun onError(error: IOException) {
                getView().onError(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSON(error)
            }
        })
    }

    override fun getTopTrack(activity: Activity, artista: String) {
        listernerService.getTopTrack(activity,artista,object :CallBack<List<TopTrackDTO>,IOException>{
            override fun onSucess(response: List<TopTrackDTO>) {
                getView().onSucessTopTrack(response)
            }

            override fun onError(error: IOException) {
                getView().onError(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSON(error)
            }
        })
    }

    override fun getTopAlbuns(activity: Activity, artista: String) {
        listernerService.getTopAlbum(activity,artista,object : CallBack<List<AlbumDTO>,IOException>{
            override fun onSucess(response: List<AlbumDTO>) {
                getView().onSucessTopAlbuns(response)
            }

            override fun onError(error: IOException) {
                getView().onErrorTopAlbuns(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSONTopAlbuns(error)
            }
        })
    }

    override fun getArtistasRelacionados(activity: Activity, artista: String) {
        listernerService.getArtistasRelacionados(activity,artista,object : CallBack<List<AlbumDTO>,IOException>{

            override fun onSucess(response: List<AlbumDTO>) {
                getView().onSucessArtistasRelacionados(response)
            }

            override fun onError(error: IOException) {
                getView().onErrorArtistasRelacionados(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSONArtistasRelacionados(error)
            }
        })
    }
}