package com.ditclear.paonet.view.article

import android.view.View
import android.webkit.*
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.ArticleDetailActivityBinding
import com.ditclear.paonet.di.component.AppComponent
import com.ditclear.paonet.di.component.DaggerAppComponent
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.Constants
import javax.inject.Inject




/**
 * 页面描述：
 *
 * Created by ditclear on 2017/10/1.
 */
class ArticleDetailActivity : BaseActivity<ArticleDetailActivityBinding>(){

    @Inject
    lateinit var paoService:PaoService

    private val component: AppComponent by lazy { DaggerAppComponent.builder().appModule(AppModule(application)).build()}


    override fun getLayoutId(): Int = R.layout.article_detail_activity

    private lateinit var webChromeClient: WebChromeClient

    override fun loadData() {
        val article: Article? = intent?.extras?.getSerializable(Constants.KEY_SERIALIZABLE) as Article?
        if(article?.id!=null) {
            paoService.getArticleDetail(article.id).compose(bindToLifecycle()).async()
                    .subscribe { t: Article? ->
                        mBinding.toolbar.title = t?.title
                        mBinding.webView.loadMarkdown(t?.content,"file:///android_asset/markdown.css")
                    }
        }

    }

    override fun initView() {

        component.inject(this)
        if (supportActionBar==null){
            setSupportActionBar(mBinding.toolbar)
        }
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
                mBinding.toolbar.title = title
            }

            override fun onGeolocationPermissionsShowPrompt(origin: String,
                                                            callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }
        }

        mBinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse {
                //System.out.println("WebResourceResponse::"+url);
                if (url.contains("[inject]")) {
                    val localPath = url.replaceFirst("^http.*inject\\]".toRegex(), "")
                    println("LocalPath::" + localPath)
                    try {
                        val `is` = applicationContext.assets.open(localPath)
                        return WebResourceResponse("text/javascript", "UTF-8", `is`)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return super.shouldInterceptRequest(view, url)
                    }

                } else {
                    return super.shouldInterceptRequest(view, url)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.webView.onPause();
        mBinding.webView.destroy();
        System.gc();
    }

}