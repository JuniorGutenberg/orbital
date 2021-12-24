package com.orbital.buscar.di.component

import com.orbital.buscar.di.module.SongsLocalModule
import com.orbital.buscar.view.fragment.BuscarFragment
import dagger.Component

@Component (modules = [SongsLocalModule::class])
interface SongsLocalComponent {
    fun inject(fragment: BuscarFragment)
}