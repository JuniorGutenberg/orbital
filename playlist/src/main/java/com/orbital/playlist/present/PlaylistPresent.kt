package com.orbital.playlist.present

import android.app.Activity
import com.orbital.core.dto.CallBack
import com.orbital.playlist.dto.OnlineDTO
import com.orbital.playlist.model.PlaylistPresenter
import com.orbital.playlist.present.contract.IPlaylistContract
import com.orbital.playlist.service.PlaylistService
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

class PlaylistPresent @Inject constructor(view: IPlaylistContract.IPlaylistView, private var playlistService: PlaylistService):
    PlaylistPresenter<IPlaylistContract.IPlaylistView>(view),IPlaylistContract.IPlaylistPresent{
    override fun getSongs(id: String, activity: Activity) {
        playlistService.getSongs(id,activity,object : CallBack<List<OnlineDTO>,IOException>{

            override fun onSucess(response: List<OnlineDTO>) {
                getView().onSucess(response)
            }

            override fun onError(error: IOException) {
                getView().onError(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSON(error)
            }

        })
    }
}