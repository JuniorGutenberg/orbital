package com.orbital.top.di.component

import com.orbital.top.di.module.TopArtistaModule
import com.orbital.top.view.fragment.TopFragment
import dagger.Component

@Component (modules = [TopArtistaModule::class])
interface TopArtistaComponent {
    fun inject(fragment: TopFragment)
}