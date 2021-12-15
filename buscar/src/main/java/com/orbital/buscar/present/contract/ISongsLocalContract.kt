package com.orbital.buscar.present.contract

import android.app.Activity
import com.orbital.buscar.dto.ArtistaDTO
import com.orbital.buscar.dto.OnlineDTO
import com.orbital.buscar.dto.SongsLocalDTO
import org.json.JSONException
import java.io.IOException

interface ISongsLocalContract {
    interface ISongsLocalView{
        fun onSucess(response: List<SongsLocalDTO>)
        fun onError()

        fun onSucessOnline(response: List<OnlineDTO>)
        fun onErrorOnline(error: IOException)
        fun onErrorJSONOnline(error: JSONException)

        fun onSucessRelacionados(response: List<OnlineDTO>)
        fun onErrorRelacionados(error: IOException)
        fun onErrorJSONRelacionados(error: JSONException)

        fun onSucessArtista(response: List<ArtistaDTO>)
        fun onErrorArtista(error: IOException)
        fun onErrorJSONArtista(error: JSONException)

    }
    interface ISongsLocalPresenter{
        fun getSongsLocal(key:String,activity: Activity)
        fun getOnline(key:String,activity: Activity)
        fun getArtista(key:String,activity: Activity)
    }
}