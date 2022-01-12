package com.orbital.machine.di.components

import com.orbital.machine.di.module.MachineMainModule
import com.orbital.machine.view.activity.MachineMainActivity
import dagger.Component

@Component (modules = [MachineMainModule::class])
interface MachineMainComponent {
    fun inject(mainActivity: MachineMainActivity)
}