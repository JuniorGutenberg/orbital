package com.orbital.machine.service.impl.async

import android.os.AsyncTask
import android.util.Log
import com.orbital.core.dto.CallBack
import com.orbital.machine.dto.TracksDTO
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

class MachineResultAsync(private var url:String, private var callBack: CallBack<List<TracksDTO>, IOException>,
                         private var itemsTrack:MutableList<TracksDTO>,private var list: List<String>,private var final:Int,
                         private var atual:Int
): AsyncTask<String, String, List<TracksDTO>>() {
    private var line = ""
    override fun doInBackground(vararg p0: String?): List<TracksDTO> {
        try{
            url = "{\"items\":$url}"
            val jsonObject = JSONObject(url)
            val items = jsonObject.getJSONArray("items")
            val tam = if(items.length()>=5){
                5
            }else{
                items.length()
            }
            for(i in 0 until tam){
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
                    itemsTrack.add(TracksDTO(null,title,null,id,thumb,views,null,null,canal))
                    }
                }
                return itemsTrack

            } catch (e: JSONException) {
                callBack.onErrorJSON(e)
                return arrayListOf()

            }
    }

    override fun onPostExecute(result: List<TracksDTO>?) {
        super.onPostExecute(result)
        if(atual == final){
            result?.let { callBack.onSucess(it) }
        }else{
            buildJson()
        }
    }

    private fun getUrl(key:String):String{
        val url = "https://www.youtube.com/results?search_query=$key&sp=EgIQAQ%253D%253D"
        return url.replace(" ","%20")
    }

    private fun buildJson(){
        atual++
        val thread = Thread{
            try {
                val url = URL(getUrl(list[atual-1]))
                val httpConn = url.openConnection()
                httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393")

                val input = BufferedReader(InputStreamReader(httpConn.inputStream))
                val iterator = input.lineSequence().iterator()


                while (iterator.hasNext()) {
                    var line = iterator.next()

                    if (line.contains("ytInitialData = {")) {
                        try{
                            /**
                             * Caso Sorriso Marato: Ao salvar  a pagina html, o codigo vem diferente, por isso os detalhes mudam,
                             *  poode acontecer com mais pesquisas
                             * */
                            if(line.contains("tents\":[{\"vi")){
                                line = line.substring(line.indexOf("tents\":[{\"vi") + 7)
                                line = line.substring(0, line.indexOf("}]}}],\"trackingParams\""))+"}]}}]"
                                MachineResultAsync(line,callBack,itemsTrack,list,final,atual).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

                            }else if(line.contains("}},{\"videoRenderer\":{\"")){
                                line = line.substring(line.indexOf("}},{\"videoRenderer\":{\"") + 3)
                                line = "["+line.substring(0, line.indexOf("}]}}],\"trackingParams\""))+"}]}}]"
                                MachineResultAsync(line,callBack,itemsTrack,list,final,atual).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                            }

                        }catch (e: Exception){
                            Log.e("Error Lines:",e.toString())
                        }
                    }
                }
                input.close()

            }catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch  (e: IOException) {
                e.printStackTrace()
            }
        }
        thread.start()
    }
}