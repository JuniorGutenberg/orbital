package com.orbital.playlist.service

import android.app.Activity
import com.orbital.core.dto.CallBack
import com.orbital.playlist.dto.OnlineDTO
import java.io.IOException

interface PlaylistService {
    fun getSongs(id:String,activity: Activity, callBack: CallBack<List<OnlineDTO>,IOException>)
}