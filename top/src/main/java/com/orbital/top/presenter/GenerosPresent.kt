package com.orbital.top.presenter

import android.app.Activity
import com.android.volley.VolleyError
import com.orbital.top.dto.GenerosDTO
import com.orbital.top.model.GenerosPresenter
import com.orbital.top.presenter.contract.IGenerosContract
import com.orbital.top.service.IGenerosService
import org.json.JSONException
import javax.inject.Inject

class GenerosPresent @Inject constructor(view:IGenerosContract.IGenerosView, private var iGenerosService: IGenerosService):
    GenerosPresenter<IGenerosContract.IGenerosView>(view),IGenerosContract.IGenerosPresenter {
    override fun getPlaylist(activity: Activity, nome: String) {
        iGenerosService.getPlaylist(activity,nome, object: com.orbital.core.dto.CallBack<List<GenerosDTO>,VolleyError>{

            override fun onSucess(response: List<GenerosDTO>) {
                getView().onSucess(response)
            }

            override fun onError(error: VolleyError) {
                getView().onError(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSON(error)
            }
        })
    }
}