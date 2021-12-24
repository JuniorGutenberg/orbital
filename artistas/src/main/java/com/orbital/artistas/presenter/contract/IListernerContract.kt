package com.orbital.artistas.presenter.contract

import android.app.Activity
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.artistas.dto.TopTrackDTO
import org.json.JSONException
import java.io.IOException

interface IListernerContract {
    interface IListernerView{
        fun onSucess(response:String)
        fun onError(error:IOException)
        fun onErrorJSON(error:JSONException)


        fun onSucessTopTrack(response:List<TopTrackDTO>)
        fun onErrorTopTrack(error:IOException)
        fun onErrorJSONTopTrack(error:JSONException)

        fun onSucessTopAlbuns(response:List<AlbumDTO>)
        fun onErrorTopAlbuns(error:IOException)
        fun onErrorJSONTopAlbuns(error:JSONException)

        fun onSucessArtistasRelacionados(response:List<AlbumDTO>)
        fun onErrorArtistasRelacionados(error:IOException)
        fun onErrorJSONArtistasRelacionados(error:JSONException)
    }
    interface IListernerPresenter{
        fun getListerner(activity: Activity, artista:String)
        fun getTopTrack(activity: Activity, artista:String)
        fun getTopAlbuns(activity: Activity, artista:String)
        fun getArtistasRelacionados(activity: Activity, artista:String)
    }
}