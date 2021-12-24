package com.orbital.buscar.di.module

import com.orbital.buscar.present.SongsLocalPresent
import com.orbital.buscar.present.contract.ISongsLocalContract
import com.orbital.buscar.service.SongsLocalService
import com.orbital.buscar.service.impl.SongsLocalImple
import dagger.Module
import dagger.Provides

@Module
class SongsLocalModule(private var view: ISongsLocalContract.ISongsLocalView) {

    @Provides
    fun provideView():ISongsLocalContract.ISongsLocalView{
        return view
    }

    @Provides
    fun providePresenter(
        view: ISongsLocalContract.ISongsLocalView,
        songsLocalService: SongsLocalService
    ):ISongsLocalContract.ISongsLocalPresenter{

        return SongsLocalPresent(view,songsLocalService)
    }

    @Provides
    fun provideService():SongsLocalService{
        return SongsLocalImple()
    }
}