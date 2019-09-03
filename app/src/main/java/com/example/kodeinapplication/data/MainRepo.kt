package com.example.kodeinapplication.data

import android.util.Log
import android.util.Xml
import androidx.lifecycle.MutableLiveData
import com.example.kodeinapplication.RestApiService
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.xmlpull.v1.XmlPullParser
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.io.StringReader

class MainRepo(override val kodein: Kodein) : KodeinAware {

    private val retrofit: Retrofit by kodein.instance()
    private var items = mutableListOf<Item>()
    private var mutableLiveData = MutableLiveData<List<Item>>()

    fun getData(): MutableLiveData<List<Item>> {
            retrofit.create(RestApiService::class.java).getPopularBlog().enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val mresponse = response.body().toString()
                items = parseData(mresponse).toMutableList()
                mutableLiveData.value = items
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {

                Log.d(this::class.java.simpleName, t.toString())
            }
        })
        return mutableLiveData
    }

    fun parseData(mresponse: String): List<Item> {
        var pubDate: String? = null
        var link: String? = null
        var description: String? = null
        var isItem = false
        val items: ArrayList<Item> = arrayListOf()

        val xmlPullParser = Xml.newPullParser()
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        xmlPullParser.setInput(StringReader(mresponse))
        xmlPullParser.nextTag()
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
            val eventType = xmlPullParser.eventType

            val name = xmlPullParser.name ?: continue

            if (eventType == XmlPullParser.END_TAG) {
                if (name.equals("item", true)) {
                    isItem = false
                }
                continue
            }

            if (eventType == XmlPullParser.START_TAG) {
                if (name.equals("item", true)) {
                    isItem = true
                    continue
                }
            }

            Log.d("MyXmlParser", "Parsing name ==> $name")
            var result = ""
            if (xmlPullParser.next() == XmlPullParser.TEXT) {
                result = xmlPullParser.text
                xmlPullParser.nextTag()
            }

            when {
                name.equals("pubDate", true) -> pubDate = result
                name.equals("link", true) -> link = result
                name.equals("description", true) -> description = result
            }

            if (pubDate != null && link != null && description != null) {
                if (isItem) {
                    items.add(Item(pubDate, link, description))
                }
                pubDate = null
                link = null
                description = null
                isItem = false
            }
        }
        return items
    }
}