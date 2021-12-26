package com.orbital.playlist.present.contract

import android.app.Activity
import com.orbital.playlist.dto.OnlineDTO
import org.json.JSONException
import java.io.IOException

interface IPlaylistContract {
    interface IPlaylistView{
        fun onSucess(response:List<OnlineDTO>)
        fun onError(error:IOException)
        fun onErrorJSON(error:JSONException)
    }
    interface IPlaylistPresent{
        fun getSongs(id:String,activity: Activity)
    }
}