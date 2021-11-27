package com.orbital.top.model

import androidx.annotation.Nullable
import com.orbital.top.presenter.contract.ITopArtistaContract

abstract class TopArtistasPresenter<VIEW: ITopArtistaContract.ITopArtistaView> (private var view: ITopArtistaContract.ITopArtistaView){

    @Nullable
    open fun getView():VIEW?{
        return view as VIEW
    }
}