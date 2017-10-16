package com.ditclear.paonet.view.article

import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.bumptech.glide.Glide
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.ArticleDetailActivityBinding
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.Constants
import com.ditclear.paonet.view.article.viewmodel.ArticleDetailViewModel
import com.trello.rxlifecycle2.android.ActivityEvent
import org.jsoup.Jsoup
import javax.inject.Inject


/**
 * 页面描述：ArticleDetailActivity
 *
 * Created by ditclear on 2017/10/1.
 */
class ArticleDetailActivity : BaseActivity<ArticleDetailActivityBinding>(){

    override fun getLayoutId(): Int = R.layout.article_detail_activity

    private lateinit var webChromeClient: WebChromeClient

    @Inject
    lateinit var viewModel:ArticleDetailViewModel

    override fun loadData() {
        val article: Article? = intent?.extras?.getSerializable(Constants.KEY_SERIALIZABLE) as Article?
        if(article?.id!=null) {
            viewModel.loadData(article.id)
                    .compose(bindToLifecycle()).async()
                    .subscribe { t: Article? ->
                        supportActionBar?.title=t?.user?.nickname?:t?.title
                        val data=processImgSrc(t!!.content!!,Constants.HOST_PAO)
                        Glide.with(mContext).load(article.thumbnail).into(mBinding.thumbnailIv)
                        mBinding.webView.loadMarkdown(data,"file:///android_asset/markdown.css")
                    }
        }

    }

    /**
     * 将文本中的相对地址转换成对应的绝对地址
     * @param content
     * @param baseUrl
     * @return
     */
    private fun processImgSrc(content: String, baseUrl: String): String {
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

    override fun initView() {

        getComponent().inject(this)
        viewModel.lifecycle=bindToLifecycle<ActivityEvent>()
        initBackToolbar(mBinding.toolbar)
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                mBinding.progressBar.progress = newProgress
                if (newProgress == 0) {
                    mBinding.progressBar.visibility = View.VISIBLE
                } else if (newProgress == 100) {
                    mBinding.progressBar.visibility = View.GONE
                }
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
            }

            override fun onGeolocationPermissionsShowPrompt(origin: String,
                                                            callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }
        }
        mBinding.webView.webChromeClient=webChromeClient
        mBinding.webView.settings.javaScriptEnabled = true;//设置js可用

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.webView.onPause()
        mBinding.webView.destroy()
        mBinding.scrollView.removeAllViews()
        System.gc();
    }

}