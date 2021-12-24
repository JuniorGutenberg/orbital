package com.orbital.artistas.di.module

import com.orbital.artistas.presenter.ListernerPresent
import com.orbital.artistas.presenter.contract.IListernerContract
import com.orbital.artistas.service.ListernerService
import com.orbital.artistas.service.imple.ListernerServiceImpl
import dagger.Module
import dagger.Provides

@Module
class ListernerModule(private var view: IListernerContract.IListernerView) {

    @Provides
    fun provideView():IListernerContract.IListernerView{

        return view
    }

    @Provides
    fun providePresenter(
        view: IListernerContract.IListernerView,
        listernerService: ListernerService
    ):IListernerContract.IListernerPresenter{

        return ListernerPresent(view,listernerService)
    }

    @Provides
    fun provideService():ListernerService{
        return ListernerServiceImpl()
    }
}