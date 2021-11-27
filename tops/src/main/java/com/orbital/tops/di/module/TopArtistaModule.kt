package com.orbital.top.di.module

import com.orbital.top.presenter.TopArtistaPresent
import com.orbital.top.presenter.contract.ITopArtistaContract
import com.orbital.top.service.TopArtistasService
import com.orbital.top.service.TopArtistasServiceImpl
import dagger.Module
import dagger.Provides

@Module
class TopArtistaModule(private var view:ITopArtistaContract.ITopArtistaView) {

    @Provides
    fun provideView(): ITopArtistaContract.ITopArtistaView{
        return view
    }

    @Provides
    fun providePresent(
        view: ITopArtistaContract.ITopArtistaView,
        topArtistasService: TopArtistasService
    ):ITopArtistaContract.ITopArtistaPresent{
        return TopArtistaPresent(view,topArtistasService)
    }

    @Provides
    fun provideService():TopArtistasService{

        return TopArtistasServiceImpl()
    }
}