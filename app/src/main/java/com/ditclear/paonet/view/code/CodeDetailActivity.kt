package com.ditclear.paonet.view.code

import android.support.v4.widget.NestedScrollView
import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.bumptech.glide.Glide
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.CodeDetailActivityBinding
import com.ditclear.paonet.lib.extention.ToastType
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.lib.extention.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.Constants
import com.ditclear.paonet.view.code.viewmodel.CodeDetailViewModel
import com.trello.rxlifecycle2.android.ActivityEvent
import org.jsoup.Jsoup
import javax.inject.Inject

/**
 * 页面描述：ArticleDetailActivity
 *
 * Created by ditclear on 2017/10/1.
 */
class CodeDetailActivity : BaseActivity<CodeDetailActivityBinding>(), CodeDetailViewModel.CallBack {
    override fun onOverScroll() {
        finish()
        overridePendingTransition(0, 0)
    }

    override fun scrollToTop() {
        mBinding.scrollView.smoothScrollTo(0, 0)
    }

    override fun getLayoutId(): Int = R.layout.code_detail_activity

    private lateinit var webChromeClient: WebChromeClient

    @Inject
    lateinit var viewModel: CodeDetailViewModel

    override fun loadData() {
        val article: Article? = intent?.extras?.getSerializable(Constants.KEY_SERIALIZABLE) as Article?
        Glide.with(mContext).load(article?.thumbnail).into(mBinding.thumbnailIv)

        viewModel.loadData()
                .compose(bindToLifecycle()).async()
                .subscribe { t: Article? ->
                    supportActionBar?.title = t?.title
                    val data = processImgSrc(t!!.readme!!, Constants.HOST_PAO)
                    mBinding.webView.loadMarkdown(data, "file:///android_asset/markdown.css")
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
    val article: Article? = intent?.extras?.getSerializable(Constants.KEY_SERIALIZABLE) as Article?
    if (article == null) {
        toast("文章不存在", ToastType.WARNING)
        finish()
        overridePendingTransition(0, 0)
    }
    getComponent().inject(this)
    viewModel.lifecycle = bindToLifecycle<ActivityEvent>()
    viewModel.article = article!!
    viewModel.attachView(this)
    mBinding.vm = viewModel
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
    mBinding.webView.webChromeClient = webChromeClient
    mBinding.webView.settings.javaScriptEnabled = true;//设置js可用
    mBinding.scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
        if (scrollY > 400 && !mBinding.fabBottom.isShown) {
            mBinding.fabBottom.show()
        } else if (scrollY <= 400 && mBinding.fabBottom.isShown) {
            mBinding.fabBottom.hide()
        }
    }
    mBinding.overScrollLayout.setOverScrollListener { onOverScroll() }
}

override fun onDestroy() {
    super.onDestroy()
    mBinding.webView.onPause()
    mBinding.webView.destroy()
    mBinding.scrollView.removeAllViews()
    System.gc();
}

}