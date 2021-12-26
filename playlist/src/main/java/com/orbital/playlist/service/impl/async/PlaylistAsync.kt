package com.orbital.playlist.service.impl.async

import android.os.AsyncTask
import com.orbital.core.dto.CallBack
import com.orbital.playlist.dto.OnlineDTO
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PlaylistAsync(private var json:String,private var callBack: CallBack<List<OnlineDTO>,IOException>): AsyncTask<String,String,List<OnlineDTO>>(){
    private var items:MutableList<OnlineDTO> = arrayListOf()

    override fun doInBackground(vararg p0: String?): List<OnlineDTO> {
        try{
            val jsonObject = JSONObject(json)
            val content = jsonObject.getJSONArray("contents")
            for(i in 0 until content.length()){
                val videoRenderer = content.getJSONObject(i)
                if(videoRenderer.has("playlistVideoRenderer")){
                    val videoRendererObj = videoRenderer.getJSONObject("playlistVideoRenderer")

                    val id = videoRendererObj.getString("videoId")
                    val thumb   = "https://img.youtube.com/vi/$id/mqdefault.jpg"


                    val titleJson = videoRendererObj.getJSONObject("title")
                    val titleArray = titleJson.getJSONArray("runs")
                    val titleObj   = titleArray.getJSONObject(0)

                    val title = titleObj.getString("text")

                    val lineTextJson = videoRendererObj.getJSONObject("shortBylineText")
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

                    items.add(OnlineDTO(null,title,null,id,thumb,views,null,null,canal))
                }
            }
        }catch (e:JSONException){
            callBack.onErrorJSON(e)

        }
        return items
    }

    override fun onPostExecute(result: List<OnlineDTO>?) {
        super.onPostExecute(result)
        result?.let { callBack.onSucess(it) }
    }
}