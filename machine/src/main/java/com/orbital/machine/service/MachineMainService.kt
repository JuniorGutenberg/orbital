package com.orbital.machine.service

import android.app.Activity
import com.orbital.core.dto.CallBack
import com.orbital.machine.dto.ArtistaDTO
import com.orbital.machine.dto.TracksDTO
import com.orbital.machine.dto.callback.CallbackNumber
import java.io.IOException

interface MachineMainService {
    fun searchArtist(key:String,activity: Activity, callBack: CallbackNumber<List<ArtistaDTO>,IOException>)
    fun getTracks(list: List<String>,activity: Activity,callBack: CallBack<List<TracksDTO>,IOException>)
}