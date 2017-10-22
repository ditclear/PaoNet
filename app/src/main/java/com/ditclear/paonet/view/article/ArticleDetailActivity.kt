package com.ditclear.paonet.view.article

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.ArticleDetailActivityBinding
import com.ditclear.paonet.lib.extention.ToastType
import com.ditclear.paonet.lib.extention.getCompactColor
import com.ditclear.paonet.lib.extention.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.helper.Constants
import com.ditclear.paonet.view.article.viewmodel.ArticleDetailViewModel
import com.trello.rxlifecycle2.android.ActivityEvent
import javax.inject.Inject


/**
 * 页面描述：ArticleDetailActivity
 *
 * Created by ditclear on 2017/10/1.
 */
class ArticleDetailActivity : BaseActivity<ArticleDetailActivityBinding>(), ArticleDetailViewModel.CallBack {


    override fun getLayoutId(): Int = R.layout.article_detail_activity

    private lateinit var webChromeClient: WebChromeClient

    @Inject
    lateinit var viewModel: ArticleDetailViewModel

    override fun loadData() {

        viewModel.loadData()

    }


    //是否收藏过
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