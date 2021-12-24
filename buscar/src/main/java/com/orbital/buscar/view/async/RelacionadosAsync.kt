package com.orbital.buscar.view.async

import android.os.AsyncTask
import com.orbital.buscar.dto.CallbackOnline
import com.orbital.buscar.dto.OnlineDTO
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RelacionadosAsync(private var json: String,private var callbackOnline: CallbackOnline<List<OnlineDTO>, IOException>): AsyncTask<String, String, List<OnlineDTO>>() {

    private val itemsYt:MutableList<OnlineDTO> = arrayListOf()

    override fun doInBackground(vararg p0: String?): List<OnlineDTO> {
        try {
            json = "{\"items\":[$json]}"
            val jsonObject = JSONObject(json)
            val items = jsonObject.getJSONArray("items")
            for(i in 0 until items.length()){
                val videoRenderer = items.getJSONObject(i)
                if(videoRenderer.has("compactVideoRenderer")){
                    val videoRendererObj = videoRenderer.getJSONObject("compactVideoRenderer")

                    val id = videoRendererObj.getString("videoId")
                    val thumb   = "https://img.youtube.com/vi/$id/mqdefault.jpg"


                    val titleJson = videoRendererObj.getJSONObject("title")

                    val title = titleJson.getString("simpleText")

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

                    itemsYt.add(OnlineDTO(null,title,null,id,thumb,views,null,null,canal))
                }
            }
            return itemsYt

        } catch (e: JSONException) {
            return arrayListOf()

        }
    }

    override fun onPostExecute(result: List<OnlineDTO>?) {
        super.onPostExecute(result)
        result?.let { callbackOnline.onSucess(it) }
    }
}