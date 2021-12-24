package com.orbital.buscar.dto

import org.json.JSONException

interface CallbackOnline<DTO_SUCESS,DTO_ERROR> {
    fun onSucess(response: DTO_SUCESS)
    fun onError(error: DTO_ERROR)
    fun onErrorJSON(error: JSONException)
}