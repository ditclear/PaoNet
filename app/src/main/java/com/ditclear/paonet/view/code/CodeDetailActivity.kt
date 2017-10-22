package com.ditclear.paonet.view.code

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.bumptech.glide.Glide
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.CodeDetailActivityBinding
import com.ditclear.paonet.lib.extention.ToastType
import com.ditclear.paonet.lib.extention.getCompactColor
import com.ditclear.paonet.lib.extention.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.helper.Constants
import com.ditclear.paonet.view.code.viewmodel.CodeDetailViewModel
import com.trello.rxlifecycle2.android.ActivityEvent
import javax.inject.Inject

/**
 * 页面描述：ArticleDetailActivity
 *
 * Created by ditclear on 2017/10/1.
 */
class CodeDetailActivity : BaseActivity<CodeDetailActivityBinding>(), CodeDetailViewModel.CallBack {


    override fun getLayoutId(): Int = R.layout.code_detail_activity

    private lateinit var webChromeClient: WebChromeClient

    @Inject
    lateinit var viewModel: CodeDetailViewModel

    override fun loadData() {
        val article: Article? = intent?.extras?.getSerializable(Constants.KEY_SERIALIZABLE) as Article?
        Glide.with(mContext).load(article?.thumbnail).into(mBinding.thumbnailIv)

        viewModel.loadData()
    }

    override fun isStow(stow: Boolean) {
        mBinding.fab.drawable.setTint(
                if (stow){
                    getCompactColor(R.color.stow_color)
                }else{
                    getCompactColor(R.color.tools_color)
                })
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
        }
        mBinding.webView.webChromeClient = webChromeClient
        mBinding.webView.settings.javaScriptEnabled = true;//设置js可用
    }

    override fun onDestroy() {
        mBinding.webView.clearHistory()
        mBinding.webView.onPause()
        mBinding.webView.destroy()
        mBinding.scrollView.removeAllViews()
        System.gc();
        super.onDestroy()
    }

}