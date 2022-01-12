package com.orbital.machine.service.impl

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import com.orbital.core.dto.CallBack
import com.orbital.core.service.BaseService
import com.orbital.machine.dto.ArtistaDTO
import com.orbital.machine.dto.TracksDTO
import com.orbital.machine.dto.callback.CallbackNumber
import com.orbital.machine.service.MachineMainService
import com.orbital.machine.service.impl.async.MachineResultAsync
import com.orbital.machine.service.impl.async.SearchArtistPart1
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

class MachineMainServiceImpl:BaseService(),MachineMainService {
    override fun searchArtist(
        key: String,
        activity: Activity,
        callBack: CallbackNumber<List<ArtistaDTO>, IOException>
    ) {
        SearchArtistPart1(getUrlSearch(key),callBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    override fun getTracks(
        list: List<String>,
        activity: Activity,
        callBack: CallBack<List<TracksDTO>, IOException>
    ) {
        val thread = Thread{
            try {
                val url = URL(getUrl(list[0]))
                val httpConn = url.openConnection()
                httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393")

                val input = BufferedReader(InputStreamReader(httpConn.inputStream))
                val iterator = input.lineSequence().iterator()
                val items:MutableList<TracksDTO> = arrayListOf()


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
                                MachineResultAsync(line,callBack,items,list,list.size,1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                            }else if(line.contains("}},{\"videoRenderer\":{\"")){
                                line = line.substring(line.indexOf("}},{\"videoRenderer\":{\"") + 3)
                                line = "["+line.substring(0, line.indexOf("}]}}],\"trackingParams\""))+"}]}}]"
                                MachineResultAsync(line,callBack,items,list,list.size,1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
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

    private fun getUrl(key:String):String{
        val url = "https://www.youtube.com/results?search_query=$key&sp=EgIQAQ%253D%253D"
        return url.replace(" ","%20")
    }

    private fun getUrlSearch(key: String):String{
        val keyurl = key.replace(" ","+")

        return "http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=$keyurl&api_key=1af5ba4e64c1bdb88199b41e109e6ecf&format=json"
    }
}