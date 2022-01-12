package com.orbital.machine.present

import android.app.Activity
import com.orbital.core.dto.CallBack
import com.orbital.machine.dto.ArtistaDTO
import com.orbital.machine.dto.TracksDTO
import com.orbital.machine.dto.callback.CallbackNumber
import com.orbital.machine.model.MachineMainPresenter
import com.orbital.machine.present.contract.IMachineMainContract
import com.orbital.machine.service.MachineMainService
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

class MachineMainPresent @Inject constructor(view: IMachineMainContract.IMachineMainView, private var machineMainService: MachineMainService):
    MachineMainPresenter<IMachineMainContract.IMachineMainView>(view),IMachineMainContract.IMachineMainPresent{
    override fun searchArtist(key: String, activity: Activity) {
        machineMainService.searchArtist(key,activity, object : CallbackNumber<List<ArtistaDTO>,IOException>{

            override fun onSucess(response: List<ArtistaDTO>, number:Int) {
                getView().onSucess(response,number)
            }

            override fun onError(error: IOException) {
                getView().onError(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSON(error)
            }

        })
    }

    override fun buildPlaylist(list: List<String>, activity: Activity) {
        machineMainService.getTracks(list,activity, object : CallBack<List<TracksDTO>,IOException>{
            override fun onSucess(response: List<TracksDTO>) {
                getView().onSucessMachineResult(response,0)
            }

            override fun onError(error: IOException) {
                getView().onError(error)
            }

            override fun onErrorJSON(error: JSONException) {
                getView().onErrorJSON(error)
            }
        })
    }
}