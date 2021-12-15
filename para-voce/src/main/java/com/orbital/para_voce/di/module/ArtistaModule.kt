package com.orbital.para_voce.di.module

import com.orbital.para_voce.presenter.ArtistaPresent
import com.orbital.para_voce.presenter.contract.IArtistaContract
import com.orbital.para_voce.service.ArtistasService
import com.orbital.para_voce.service.imple.ArtistaServiceImpl
import dagger.Module
import dagger.Provides

@Module
class ArtistaModule(private var view:IArtistaContract.ArtistaView) {

    @Provides
    fun provideView():IArtistaContract.ArtistaView{
        return view
    }

    @Provides
    fun providePresenter(
        view: IArtistaContract.ArtistaView,
        artistasService: ArtistasService
    ): IArtistaContract.AritstaPresenter{

      return  ArtistaPresent(view,artistasService)
    }

    @Provides
    fun provideService():ArtistasService{

        return ArtistaServiceImpl()
    }
}