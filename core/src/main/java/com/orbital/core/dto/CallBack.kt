package com.orbital.core.dto

import org.json.JSONException

interface CallBack<DTO_SUCESS,DTO_ERROR> {
    fun onSucess(response: DTO_SUCESS)
    fun onError(error:DTO_ERROR)
    fun onErrorJSON(error:JSONException)
}