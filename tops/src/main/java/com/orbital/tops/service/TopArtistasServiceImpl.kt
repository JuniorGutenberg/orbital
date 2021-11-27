package com.orbital.top.service

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.orbital.top.dto.CallBack
import com.orbital.top.dto.TopArtistasDTO
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

class TopArtistasServiceImpl:BaseService(),TopArtistasService {

    override fun getTopArtistas(activity:Activity,callback: CallBack<List<TopArtistasDTO>, VolleyError>) {
        val jsonObject = arrayOfNulls<JSONObject>(1)

        val stringRequests = arrayOfNulls<StringRequest>(1)

        val requestQueues = arrayOfNulls<RequestQueue>(1)

        val urlVolley = "https://orbitalprobd.com/get.php"

        requestQueues[0] = Volley.newRequestQueue(activity)
        val arrayListTopArtista = ArrayList<String>()
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
                            val name: String = jsonObject[0]!!.getString("artistas")
                            val separeteTop = name.split(",").toTypedArray()
                            for (s2 in separeteTop.indices) {
                                arrayListTopArtista.add(separeteTop[s2])
                            }

                        }
                    } catch (e: JSONException) {
                        callback.onErrorJSON(e)
                    }
                }
                try {
                    callback.onSucess(getInfoArtistas(arrayListTopArtista).execute().get())
                }catch (e:Exception){
                    e.message?.let { Log.e("Error Async:", it) }
                }



            }, Response.ErrorListener {
                callback.onError(it)
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["key"] = "999d3a54-e45e-40c7-8d09-1e911f32406b"
                params["sessao"] = "topArtistas"
                return params
            }
        }
        requestQueues[0]?.add(stringRequests[0])
    }


}
class getInfoArtistas(private var arrayList: ArrayList<String>): AsyncTask<String, String, MutableList<TopArtistasDTO>>() {
    var image:String = ""
    val items:MutableList<TopArtistasDTO> = arrayListOf()
    override fun doInBackground(vararg p0: String?): MutableList<TopArtistasDTO> {
        for (i in arrayList.indices) {
            setImages(arrayList[i])
        }
        return items
    }

    override fun onPostExecute(result: MutableList<TopArtistasDTO>?) {
        super.onPostExecute(result)

    }
    private fun setImages(artista: String?) {
        Thread {
            var doc: Document? = null
            try {
                doc = Jsoup.connect("https://www.last.fm/music/$artista/+images").get()
                val elementsByTag: org.jsoup.select.Elements = doc.getElementsByTag("meta")
                var i2 = 0
                for (element in elementsByTag) {
                    if (i2 == 9) {
                        if (!element.attr("content").contains("2a96cbd8b46e442fc41c2b86b821562f")) {
                            image = element.attr("content")
                        }
                    }
                    i2++
                }
                if (artista != null) {
                    if (artista != "" && image != "") {
                        if (!image.contains("2a96cbd8b46e442fc41c2b86b821562f")) {
                            items.add(TopArtistasDTO(artista,image))
                            Log.e("GET_TOP","onSucess")
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}