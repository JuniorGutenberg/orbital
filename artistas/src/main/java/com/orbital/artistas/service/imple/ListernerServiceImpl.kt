package com.orbital.artistas.service.imple

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.artistas.dto.TopTrackDTO
import com.orbital.artistas.service.ListernerService
import com.orbital.artistas.service.imple.async.ArtistasRelacionadosAsync
import com.orbital.artistas.service.imple.async.ListernerAsync
import com.orbital.artistas.service.imple.async.TopAlbunsAsync
import com.orbital.artistas.service.imple.async.TopTracksAsync
import com.orbital.core.dto.CallBack
import com.orbital.core.service.BaseService
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class ListernerServiceImpl:BaseService(), ListernerService {
    override fun getListerners(activity: Activity,artista:String ,callBack: CallBack<String, IOException>) {
        ListernerAsync(getUrlListerner(artista),callBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    override fun getTopTrack(
        activity: Activity,
        artista: String,
        callBack: CallBack<List<TopTrackDTO>, IOException>
    ) {
        val thread = Thread{
            try {
                val url = URL(getUrl(artista))
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
                                TopTracksAsync(line,callBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                            }else if(line.contains("}},{\"videoRenderer\":{\"")){
                                line = line.substring(line.indexOf("}},{\"videoRenderer\":{\"") + 3)
                                line = "["+line.substring(0, line.indexOf("}]}}],\"trackingParams\""))+"}]}}]"
                                TopTracksAsync(line,callBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                            }

                        }catch (e: Exception){
                            Log.e("Error Lines:",e.toString())
                        }
                    }
                }
                input.close()

            }catch (e: MalformedURLException) {
                e.printStackTrace();
            } catch  (e: IOException) {
                e.printStackTrace();
            }
        }
        thread.start()
    }

    override fun getTopAlbum(
        activity: Activity,
        artista: String,
        callBack: CallBack<List<AlbumDTO>, IOException>
    ) {
        TopAlbunsAsync(getUrlAlbum(artista),callBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    override fun getArtistasRelacionados(
        activity: Activity,
        artista: String,
        callBack: CallBack<List<AlbumDTO>, IOException>
    ) {
        ArtistasRelacionadosAsync(getUrlArtistaRelacionados(artista),callBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    private fun getUrl(key:String):String{
        val url = "https://www.youtube.com/results?search_query=$key&sp=EgIQAQ%253D%253D"
        return url.replace(" ","%20")
    }
    private fun getUrlListerner(artista: String):String{
        var artistaUrl = artista.lowercase(Locale.getDefault())
        artistaUrl = artistaUrl.replace(" ", "+")
        if (artistaUrl.contains("&")) {
            artistaUrl = artistaUrl.replace("&", "%26")
        }
        return "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=$artistaUrl&api_key=1af5ba4e64c1bdb88199b41e109e6ecf&format=json"
    }
    private fun getUrlAlbum(artista: String):String{
        var artistaUrl = artista.lowercase(Locale.getDefault())
        artistaUrl = artistaUrl.replace(" ", "+")
        if (artistaUrl.contains("&")) {
            artistaUrl = artistaUrl.replace("&", "%26")
        }
        return "http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=$artistaUrl&api_key=1af5ba4e64c1bdb88199b41e109e6ecf&format=json"
    }
    private fun getUrlArtistaRelacionados(artista: String):String{
        var artistaUrl = artista.lowercase(Locale.getDefault())
        artistaUrl = artistaUrl.replace(" ", "+")
        if (artistaUrl.contains("&")) {
            artistaUrl = artistaUrl.replace("&", "%26")
        }
        return "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=$artistaUrl&api_key=1af5ba4e64c1bdb88199b41e109e6ecf&format=json"
    }
}