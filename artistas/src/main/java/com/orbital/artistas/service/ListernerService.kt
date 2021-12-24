package com.orbital.artistas.service

import android.app.Activity
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.artistas.dto.TopTrackDTO
import com.orbital.core.dto.CallBack
import java.io.IOException

interface ListernerService {
    fun getListerners(activity: Activity,artista:String,callBack: CallBack<String,IOException>)
    fun getTopTrack(activity: Activity,artista:String,callBack: CallBack<List<TopTrackDTO>,IOException>)
    fun getTopAlbum(activity: Activity,artista:String,callBack: CallBack<List<AlbumDTO>,IOException>)
    fun getArtistasRelacionados(activity: Activity,artista:String,callBack: CallBack<List<AlbumDTO>,IOException>)
}