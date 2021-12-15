package com.orbital.para_voce.model

import androidx.annotation.Nullable
import com.orbital.para_voce.presenter.contract.IArtistaContract

abstract class ArtistaPresenter<VIEW: IArtistaContract.ArtistaView>(private var view: IArtistaContract.ArtistaView) {
    @Nullable
    open fun getView():VIEW{
        return view as VIEW
    }
}