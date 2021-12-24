package com.orbital.artistas.service.imple.async

import android.os.AsyncTask
import com.orbital.artistas.dto.TopTrackDTO
import com.orbital.core.dto.CallBack
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class TopTracksAsync (private var json: String,private var callbackOnline: CallBack<List<TopTrackDTO>, IOException>):
    AsyncTask<String, String, List<TopTrackDTO>>() {

    private val itemsYt:MutableList<TopTrackDTO> = arrayListOf()

    override fun doInBackground(vararg p0: String?): List<TopTrackDTO> {
        try {
            json = "{\"items\":$json}"
            val jsonObject = JSONObject(json)
            val items = jsonObject.getJSONArray("items")
            for(i in 0 until items.length()){
                val videoRenderer = items.getJSONObject(i)
                if(videoRenderer.has("videoRenderer")){
                    val videoRendererObj = videoRenderer.getJSONObject("videoRenderer")

                    val id = videoRendererObj.getString("videoId")
                    val thumb   = "https://img.youtube.com/vi/$id/mqdefault.jpg"


                    val titleJson = videoRendererObj.getJSONObject("title")
                    val titleArray = titleJson.getJSONArray("runs")
                    val titleObj   = titleArray.getJSONObject(0)

                    val title = titleObj.getString("text")

                    val lineTextJson = videoRendererObj.getJSONObject("longBylineText")
                    val lineTextArray = lineTextJson.getJSONArray("runs")
                    val lineTextObj   = lineTextArray.getJSONObject(0)

                    val canal = lineTextObj.getString("text")
                    var views:String? = null

                    if(videoRendererObj.has("viewCountText")) {
                        val viewCountText = videoRendererObj.getJSONObject("viewCountText")
                        if (viewCountText.has("simpleText")) {
                            views = viewCountText.getString("simpleText")
                        }

                    }

                    itemsYt.add(TopTrackDTO(null,title,null,id,thumb,views,null,null,canal))
                }
            }
            return itemsYt

        } catch (e: JSONException) {
            return arrayListOf()

        }
    }

    override fun onPostExecute(result: List<TopTrackDTO>?) {
        super.onPostExecute(result)
        result?.let { callbackOnline.onSucess(it) }
    }
}