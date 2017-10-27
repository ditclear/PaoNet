package com.ditclear.paonet.view.article

import android.support.v4.widget.NestedScrollView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.ArticleDetailActivityBinding
import com.ditclear.paonet.lib.extention.ToastType
import com.ditclear.paonet.lib.extention.getCompactColor
import com.ditclear.paonet.lib.extention.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.article.viewmodel.ArticleDetailViewModel
import com.ditclear.paonet.view.helper.Constants
import javax.inject.Inject


/**
 * 页面描述：ArticleDetailActivity
 *
 * Created by ditclear on 2017/10/1.
 */
class ArticleDetailActivity : BaseActivity<ArticleDetailActivityBinding>() {


    override fun getLayoutId(): Int = R.layout.article_detail_activity

    @Inject
    lateinit var viewModel: ArticleDetailViewModel

    override fun loadData() {

        viewModel.loadData().compose(bindToLifecycle())
                .subscribe({ t: Boolean? -> t?.run { isStow(t) } },
                        { t: Throwable? -> toastFailure(t!!) })

    }


    //是否收藏过
    private fun isStow(stow: Boolean) {
        mBinding.fab.drawable.setTint(
                if (stow) {
                    getCompactColor(R.color.stow_color)
                } else {
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

        viewModel.article = article!!
        mBinding.vm = viewModel.apply {
            this.article=article
        }
        mBinding.presenter=this

        initBackToolbar(mBinding.toolbar)

        mBinding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY-oldScrollY>10){
                mBinding.fab.hide()
            }else if(scrollY-oldScrollY<-10){
                mBinding.fab.show()
            }
        })
    }

    override fun onDestroy() {

        mBinding.webView.clearHistory()
        mBinding.webView.onPause()
        mBinding.webView.destroy()
        mBinding.scrollView.removeAllViews()
        System.gc();
        super.onDestroy()
    }

    @SingleClick
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab -> stow()
        }
    }

    private fun stow() {
        viewModel.stow().compose(bindToLifecycle())
                .subscribe({ t -> toastSuccess(t.message) }
                        , { t: Throwable? -> t?.run { toastFailure(this) } })
    }

}