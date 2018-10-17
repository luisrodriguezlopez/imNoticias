package com.imnoticias.lrodriguez.imnoticias

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.google.gson.JsonObject
import com.imnoticias.lrodriguez.imnoticias.R.id.async
import com.imnoticias.lrodriguez.imnoticias.R.id.end

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import retrofit2.*
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

     var itemsNews = listOf<ItemNews>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        news_list.layoutManager = GridLayoutManager(this, 1)
        getWebsite {
            Thread(Runnable {

                this.runOnUiThread {
                    kotlin.run {

                        var adapter = AdapterNews(this.itemsNews)
                        this.news_list.adapter = adapter
                    }
                }
            }).start()

        }.also {

        }

    }

    fun getWebsite(callback: () -> Unit)  {

        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/posts/").addConverterFactory(GsonConverterFactory.create()).build()
        val endpoint = retrofit.create(ServicesWebScraping::class.java)

        val call = endpoint.loadHtml()
        /*
        REST Example
        call.enqueue(object : retrofit2.Callback<Array<JsonObject>> {
            override fun onResponse(call: Call<Array<JsonObject>>, response: Response<Array<JsonObject>>) {
                println("holis")

                print(response.body())
            }

            override fun onFailure(call: Call<Array<JsonObject>>, t: Throwable) {
                println("holis")
                print(t.localizedMessage)

            }
        })
        */

        /* */
        async {

            Jsoup.connect("https://www.debate.com.mx/ajax/get_section_news.html?viewmore=/ajax/get_section_news.html&page=1&size=7&section=policiacas").get().run {
                 itemsNews = (select("article")
                ).map {
                    ItemNews("${it.select("a[href][title]").attr("title")}",
                            "https://www.debate.com.mx${it.select("img[src]").attr("src")}",
                            "blabla",
                            "https://www.debate.com.mx${it.select("a[href]").attr("href")}")
                }.also {
                     callback()
                 }

            }

        }

    }

}




fun async(run: () -> Unit) = Thread(run).start()



interface ServicesWebScraping {


    @GET("jsonplaceholder.typicode.com/posts/")
    fun loadHtml() : Call<Array<JsonObject>>
}
