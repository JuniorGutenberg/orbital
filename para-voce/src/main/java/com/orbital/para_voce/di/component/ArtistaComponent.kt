package com.orbital.para_voce.di.component

import com.orbital.para_voce.di.module.ArtistaModule
import com.orbital.para_voce.view.fragment.ParaVoceFragment
import dagger.Component

@Component (modules = [ArtistaModule::class])
interface ArtistaComponent {
    fun inject(paraVoceFragment: ParaVoceFragment)
}