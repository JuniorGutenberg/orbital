package com.orbital.machine.dto.callback

import org.json.JSONException

interface CallbackNumber <DTO_SUCESS,DTO_ERROR> {
    fun onSucess(response: DTO_SUCESS, number:Int)
    fun onError(error:DTO_ERROR)
    fun onErrorJSON(error: JSONException)
}