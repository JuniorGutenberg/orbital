package com.orbital.top.di.component

import com.orbital.top.di.module.GenerosModule
import com.orbital.top.view.activity.GenerosMainActivity
import dagger.Component

@Component (modules = [GenerosModule::class])
interface GenerosComponent {
    fun inject(generosMainActivity: GenerosMainActivity)
}