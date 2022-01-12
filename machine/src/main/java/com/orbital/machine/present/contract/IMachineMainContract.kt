package com.orbital.machine.present.contract

import android.app.Activity
import com.orbital.machine.dto.ArtistaDTO
import com.orbital.machine.dto.TracksDTO
import org.json.JSONException
import java.io.IOException

interface IMachineMainContract {
    interface IMachineMainView{
        fun onSucess(response:List<ArtistaDTO>, number:Int)
        fun onError(error:IOException)
        fun onErrorJSON(error:JSONException)

        fun onSucessMachineResult(response:List<TracksDTO>, number:Int)
        fun onErrorMachineResult(error:IOException)
        fun onErrorJSONMachineResult(error:JSONException)
    }
    interface IMachineMainPresent{
        fun searchArtist(key:String, activity: Activity)
        fun buildPlaylist(list: List<String>,activity: Activity)
    }
}