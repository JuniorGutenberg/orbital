package com.orbital.para_voce.presenter.contract

import android.app.Activity
import com.orbital.para_voce.dto.ArtistaDTO

interface IArtistaContract {
    interface ArtistaView{
        fun onSucess(response: List<ArtistaDTO>)
        fun onError()
    }
    interface AritstaPresenter{
        fun getArtistas(activity: Activity)
    }
}