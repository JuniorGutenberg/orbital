package com.orbital.machine.di.module

import com.orbital.machine.present.MachineMainPresent
import com.orbital.machine.present.contract.IMachineMainContract
import com.orbital.machine.service.MachineMainService
import com.orbital.machine.service.impl.MachineMainServiceImpl
import dagger.Module
import dagger.Provides

@Module
class MachineMainModule(private var view:IMachineMainContract.IMachineMainView) {

    @Provides
    fun provideView():IMachineMainContract.IMachineMainView{

        return view
    }

    @Provides
    fun providePresent(
        view: IMachineMainContract.IMachineMainView,
        machineMainService: MachineMainService
    ):IMachineMainContract.IMachineMainPresent{

        return MachineMainPresent(view,machineMainService)
    }

    @Provides
    fun provideService():MachineMainService{

        return MachineMainServiceImpl()
    }
}