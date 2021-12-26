package com.orbital.playlist.di.module

import com.orbital.playlist.present.PlaylistPresent
import com.orbital.playlist.present.contract.IPlaylistContract
import com.orbital.playlist.service.PlaylistService
import com.orbital.playlist.service.impl.PlaylistServiceImpl
import dagger.Module
import dagger.Provides

@Module
class PlaylistModule(private var view:IPlaylistContract.IPlaylistView) {

    @Provides
    fun provideView():IPlaylistContract.IPlaylistView{
        return view
    }

    @Provides
    fun providePresent(
        view: IPlaylistContract.IPlaylistView,
        playlistService: PlaylistService
    ):IPlaylistContract.IPlaylistPresent{
        return PlaylistPresent(view,playlistService)
    }

    @Provides
    fun provideService():PlaylistService{

        return PlaylistServiceImpl()
    }
}