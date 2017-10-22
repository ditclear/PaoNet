package com.ditclear.paonet.view.helper

import org.jsoup.Jsoup

/**
 * 页面描述：Utils
 *
 * Created by ditclear on 2017/10/22.
 */
object Utils{

    /**
     * 将文本中的相对地址转换成对应的绝对地址
     * @param content
     * @param baseUrl
     * @return
     */
    fun processImgSrc(content: String, baseUrl: String): String {
        val document = Jsoup.parse(content)
        document.setBaseUri(baseUrl)
        val elements = document.select("img[src]")
        for (el in elements) {
            val imgUrl = el.attr("src")
            if (imgUrl.trim({ it <= ' ' }).startsWith("/")) {
                el.attr("src", el.absUrl("src"))
            }
        }
        return document.html()
    }
}