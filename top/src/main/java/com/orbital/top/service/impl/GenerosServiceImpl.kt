package com.orbital.top.service.impl

import android.app.Activity
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.orbital.core.dto.CallBack
import com.orbital.core.service.BaseService
import com.orbital.top.dto.GenerosDTO
import com.orbital.top.service.IGenerosService
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class GenerosServiceImpl: BaseService(),IGenerosService {
    override fun getPlaylist(
        activity: Activity,
        nome: String,
        callBack: CallBack<List<GenerosDTO>, VolleyError>
    ) {
        val jsonObject = arrayOfNulls<JSONObject>(1)

        val stringRequests = arrayOfNulls<StringRequest>(1)

        val requestQueues = arrayOfNulls<RequestQueue>(1)

        val urlVolley = "https://orbitalprobd.com/get.php"

        val items:MutableList<GenerosDTO> = arrayListOf()


        requestQueues[0] = Volley.newRequestQueue(activity)
        stringRequests[0] = object : StringRequest(
            Method.POST, urlVolley,
            Response.Listener { response ->

                var responseS = response
                responseS = responseS.replace("[", "")
                responseS = responseS.replace("]", "")
                val separete = responseS.split("},").toTypedArray()
                val tam:Int = separete.size

                for (s in 0 until tam) {
                    if (s + 1 < separete.size) {
                        separete[s] = separete[s] + "}"
                    }
                    try {
                        jsonObject[0] = JSONObject(separete[s])
                        if (jsonObject[0] != null) {
                            items.add(
                                GenerosDTO(
                                    jsonObject[0]?.getString("name").toString(),
                                    jsonObject[0]?.getString("image").toString(),
                                    jsonObject[0]?.getString("autor").toString(),
                                    jsonObject[0]?.getString("idYoutube").toString(),
                                    jsonObject[0]?.getString("twitter").toString(),
                                    jsonObject[0]?.getString("instagram").toString()
                                )
                            )
                        }
                    } catch (e: JSONException) {
                        callBack.onErrorJSON(e)
                    }
                }
                try {
                    callBack.onSucess(items)
                }catch (e:Exception){
                    e.message?.let { Log.e("Error Async:", it) }
                }

            }, Response.ErrorListener {
                callBack.onError(it)
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["key"] = "999d3a54-e45e-40c7-8d09-1e911f32406b"
                params["sessao"] = "playlist"
                params["playlist"] = nome.replaceFirstChar { it.lowercase() }

                return params
            }
        }
        requestQueues[0]?.add(stringRequests[0])
    }
}