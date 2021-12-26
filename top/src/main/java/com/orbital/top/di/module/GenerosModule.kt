package com.orbital.top.di.module

import com.orbital.top.presenter.GenerosPresent
import com.orbital.top.presenter.contract.IGenerosContract
import com.orbital.top.service.IGenerosService
import com.orbital.top.service.impl.GenerosServiceImpl
import dagger.Module
import dagger.Provides

@Module
class GenerosModule(private var view: IGenerosContract.IGenerosView) {

    @Provides
    fun provideView():IGenerosContract.IGenerosView{
        return view
    }

    @Provides
    fun providePresent(
        view: IGenerosContract.IGenerosView,
        iGenerosService: IGenerosService
    ):IGenerosContract.IGenerosPresenter{
        return GenerosPresent(view,iGenerosService)
    }

    @Provides
    fun provideService():IGenerosService{
        return GenerosServiceImpl()
    }
}