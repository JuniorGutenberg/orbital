package com.orbital.artistas.di.component

import com.orbital.artistas.di.module.ListernerModule
import com.orbital.artistas.view.activity.ArtistasActivity
import dagger.Component

@Component (modules = [ListernerModule::class])
interface ListernerComponent {
    fun inject(artistasActivity: ArtistasActivity)
}