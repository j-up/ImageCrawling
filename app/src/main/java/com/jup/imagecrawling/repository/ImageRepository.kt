package com.jup.imagecrawling.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ImageRepository {
    /**
     * @author: JiMinLee
     * @param: [url]
     * @return: Document
     * @description: [url]의 Document를 반환한다.
     **/
    suspend fun getUrlDocument(url: String, userAgent: String="Mozilla",referrer:String="http://www.google.com"): Document? {
        var doc: Document? = null

        withContext(Dispatchers.IO) {
            try {
                doc = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .referrer(referrer)
                    .get()
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }

        return doc
    }

}