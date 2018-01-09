package com.ditclear.paonet.view.code

import android.support.v4.widget.NestedScrollView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.CodeDetailActivityBinding
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.getCompactColor
import com.ditclear.paonet.helper.extens.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.base.BaseActivity
import com.ditclear.paonet.view.code.viewmodel.CodeDetailViewModel
import com.ditclear.paonet.helper.Constants
import javax.inject.Inject

/**
 * 页面描述：ArticleDetailActivity
 *
 * Created by ditclear on 2017/10/1.
 */
class CodeDetailActivity : BaseActivity<CodeDetailActivityBinding>() {


    override fun getLayoutId(): Int = R.layout.code_detail_activity

    @Inject
    lateinit var viewModel: CodeDetailViewModel

    override fun loadData() {

        viewModel.loadData().compose(bindToLifecycle()).subscribe({ t: Boolean? -> t?.let { isStow(it) } },
                { t: Throwable? -> t?.let {toastFailure(it) } })
    }

    fun isStow(stow: Boolean) {
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
        viewModel.article=article
        mBinding.run {
            vm=viewModel
            presenter=this@CodeDetailActivity
            scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY-oldScrollY>10){
                    mBinding.fab.hide()
                }else if(scrollY-oldScrollY<-10){
                    mBinding.fab.show()
                }
            })
        }
        initBackToolbar(mBinding.toolbar)

    }

    @CheckLogin
    @SingleClick
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab -> viewModel.stow().compose(bindToLifecycle())
                    .subscribe({ t -> toastSuccess(t.message) }
                            , { t: Throwable? -> toastFailure(t ?: Exception("收藏失败")) })
        }
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