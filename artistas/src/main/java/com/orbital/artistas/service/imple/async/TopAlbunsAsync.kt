package com.orbital.artistas.service.imple.async

import android.os.AsyncTask
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.core.dto.CallBack
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class TopAlbunsAsync (private var url:String, private var callBack: CallBack<List<AlbumDTO>, IOException>): AsyncTask<String, String, List<AlbumDTO>>() {

    private var line: String = ""
    private var items:MutableList<AlbumDTO> = arrayListOf()
    private var jsonObject: JSONObject = JSONObject()
    private var image=""
    private var name=""


    override fun doInBackground(vararg p0: String?): List<AlbumDTO> {
        val okHttpClient = OkHttpClient()
        val request: okhttp3.Request = Request.Builder()
            .url(url)
            .build()
        var responses: okhttp3.Response? = null
        try {
            responses = okHttpClient.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
            callBack.onError(e)
            return items
        }
        try {
            if (responses.body != null) {
                line = responses.body!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            callBack.onError(e)
            return items
        }
        try {
            if (line.isNotEmpty()) {
                jsonObject = JSONObject(line)
                try {
                    val topalbuns = jsonObject.getJSONObject("topalbums")
                    val albuns = topalbuns.getJSONArray("album")
                    val tam: Int = if(albuns.length()>=20){
                        20
                    }else{
                        albuns.length()
                    }
                    for (i in 0 until tam) {
                        val resultAlbuns = albuns.getJSONObject(i)
                        name = resultAlbuns.getString("name")
                        val imagem = resultAlbuns.getJSONArray("image")
                        for (j in 0 until imagem.length()) {
                            val resultImagem = imagem.getJSONObject(j)
                            if (resultImagem.has("size")) {
                                if (resultImagem.getString("size") == "extralarge") {
                                    image = resultImagem.getString("#text")
                                }
                            }
                        }
                        if(image != "") {
                            items.add(AlbumDTO(image, name))
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    callBack.onErrorJSON(e)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onErrorJSON(e)
        }

        return items
    }

    override fun onPostExecute(result: List<AlbumDTO>) {
        super.onPostExecute(result)
        if(result.isNotEmpty()){
            callBack.onSucess(result)
        }
    }
}
