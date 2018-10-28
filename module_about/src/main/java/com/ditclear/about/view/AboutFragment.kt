package com.ditclear.about.view

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ditclear.about.R
import com.ditclear.about.databinding.AboutFragmentBinding
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.mine.SinglePageActivity

/**
 * 页面描述：AboutFragment
 *
 * Created by ditclear on 2018/10/27.
 */

class AboutFragment : BaseFragment<AboutFragmentBinding>() {
    override fun initView() {
        inList= false
        setWebSetting()
        (activity as SinglePageActivity?)?.needShowTab(false)
        mBinding.webView.loadMarkdownFile("file:///android_asset/about.md","file:///android_asset/markdown.css");
    }

    private fun setWebSetting() {
        val settings = mBinding.webView.settings//获取设置
        settings.setSupportZoom(true)
        settings.allowFileAccess = true
        settings.domStorageEnabled = true
        mBinding.webView.webChromeClient = WebChromeClient()
        mBinding.webView.isVerticalScrollBarEnabled = false
        settings.allowUniversalAccessFromFileURLs = true

        WebView.setWebContentsDebuggingEnabled(true)

        mBinding.webView.webViewClient = WebViewClient()

    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun getLayoutId(): Int = R.layout.about_fragment

}