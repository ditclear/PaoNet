package com.ditclear.paonet.helper.extens

import org.markdownj.MarkdownProcessor
import us.feras.mdv.MarkdownView

/**
 * 页面描述：扩展第三方库
 *
 * Created by ditclear on 2017/10/3.
 */
fun MarkdownView.loadMarkdownWithBaseURL(baseUrl: String?, txt: String?, cssFileUrl: String) {
    val m = MarkdownProcessor()
    var html = m.markdown(txt)
    html = "<link rel='stylesheet' type='text/css' href='$cssFileUrl' />$html"
    loadDataWithBaseURL(baseUrl, html, "text/html", "UTF-8", null)
}

fun MarkdownView.setMarkdown(markdown : String?){
    loadMarkdown(markdown,"file:///android_asset/markdown.css")
}
