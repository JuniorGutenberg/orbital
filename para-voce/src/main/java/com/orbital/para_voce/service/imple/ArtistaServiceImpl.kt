package com.orbital.para_voce.service.imple

import android.app.Activity
import android.os.AsyncTask
import com.android.volley.VolleyError
import com.orbital.para_voce.dto.ArtistaDTO
import com.orbital.para_voce.dto.CallBack
import com.orbital.para_voce.service.ArtistasService
import com.orbital.para_voce.service.BaseService
import com.orbital.para_voce.service.imple.async.ArtistaPart1Async

class ArtistaServiceImpl:BaseService(),ArtistasService {
    override fun getArtistas(
        activity: Activity,
        callback: CallBack<List<ArtistaDTO>, VolleyError>
    ) {
        ArtistaPart1Async(activity,callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}