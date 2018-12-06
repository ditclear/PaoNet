package com.ditclear.paonet.view.code

import android.support.v4.widget.NestedScrollView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.CodeDetailActivityBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.getCompactColor
import com.ditclear.paonet.helper.extens.set
import com.ditclear.paonet.helper.extens.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.base.BaseActivity
import com.ditclear.paonet.view.code.viewmodel.CodeDetailViewModel

/**
 * 页面描述：ArticleDetailActivity
 *
 * Created by ditclear on 2017/10/1.
 */
class CodeDetailActivity : BaseActivity<CodeDetailActivityBinding>() {


    override fun getLayoutId(): Int = R.layout.code_detail_activity

    private val viewModel :CodeDetailViewModel by lazy {
        getInjectViewModel<CodeDetailViewModel>()
    }
    private val mArticle by lazy { autoWired<Article>(Constants.KEY_SERIALIZABLE) }

    override fun loadData(isRefresh: Boolean) {

        viewModel.loadData().bindLifeCycle(this).subscribe({ isStow(it) },
                { toastFailure(it) })
    }

    fun isStow(stow: Boolean?) {
        mBinding.fab.drawable.setTint(
                if (stow == true) {
                    getCompactColor(R.color.stow_color)
                } else {
                    getCompactColor(R.color.tools_color)
                })
    }


    override fun initView() {
        if (mArticle == null) {
            toast("文章不存在", ToastType.WARNING)
            finish()
            overridePendingTransition(0, 0)
        }


        delayToTransition = true
        mBinding.vm=viewModel.apply {
            this.article.set(mArticle)
            this.nameAndDate.set("""${mArticle?.user?.nickname ?: "佚名"}
                                        |${mArticle?.pubDate}""".trimMargin())
        }
        mBinding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY - oldScrollY > 10) {
                mBinding.fab.hide()
            } else if (scrollY - oldScrollY < -10) {
                mBinding.fab.show()
            }
        })

        initBackToolbar(mBinding.toolbar)

    }

    @CheckLogin
    @SingleClick
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab -> viewModel.stow().bindLifeCycle(this)
                    .subscribe({ toastSuccess(it.message) }
                            , { toastFailure(it) })
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