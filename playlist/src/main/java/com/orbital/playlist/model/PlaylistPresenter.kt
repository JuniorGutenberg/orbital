package com.orbital.playlist.model

import androidx.annotation.Nullable
import com.orbital.playlist.present.contract.IPlaylistContract

abstract class PlaylistPresenter<VIEW: IPlaylistContract.IPlaylistView>(private var view:IPlaylistContract.IPlaylistView) {
    @Nullable
    open fun getView():IPlaylistContract.IPlaylistView{
        return view as VIEW
    }
}