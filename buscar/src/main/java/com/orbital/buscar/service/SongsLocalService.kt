package com.orbital.buscar.service

import android.app.Activity
import com.orbital.buscar.dto.*
import java.io.IOException

interface SongsLocalService {
    fun getSongs(key:String,activity: Activity, callback:CallbackSongsLocal<List<SongsLocalDTO>>)
    fun getSongsOnline(key:String, activity: Activity, calllback: CallbackOnline<List<OnlineDTO>, IOException>)
    fun getSongsRelacionados(key:String, activity: Activity, calllback: CallbackOnline<List<OnlineDTO>, IOException>)
    fun getArtista(key:String, activity: Activity, callback: CallbackOnline<List<ArtistaDTO>, IOException>)


}