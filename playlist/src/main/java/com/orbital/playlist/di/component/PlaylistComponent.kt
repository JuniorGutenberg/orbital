package com.orbital.playlist.di.component

import com.orbital.playlist.di.module.PlaylistModule
import com.orbital.playlist.view.activity.PlaylistActivity
import dagger.Component

@Component (modules = [PlaylistModule::class])
interface PlaylistComponent {
    fun inject(playlistActivity: PlaylistActivity)
}