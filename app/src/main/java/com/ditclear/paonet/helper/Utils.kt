package com.ditclear.paonet.helper

import android.content.Context
import android.os.ResultReceiver
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    fun showIme(view: View) {
        val imm = view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // the public methods don't seem to work for me, so… reflection.
        try {
            val showSoftInputUnchecked = InputMethodManager::class.java.getMethod(
                    "showSoftInputUnchecked", Int::class.javaPrimitiveType, ResultReceiver::class.java)
            showSoftInputUnchecked.isAccessible = true
            showSoftInputUnchecked.invoke(imm, 0, null)
        } catch (e: Exception) {
            // ho hum
        }

    }

    fun hideIme(view: View) {
        val imm = view.context.getSystemService(Context
                .INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}