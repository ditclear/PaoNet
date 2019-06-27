package com.ditclear.paonet.view.code

import androidx.core.widget.NestedScrollView
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
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 页面描述：ArticleDetailActivity
 *
 * Created by ditclear on 2017/10/1.
 */
class CodeDetailActivity : BaseActivity<CodeDetailActivityBinding>() {


    override fun getLayoutId(): Int = R.layout.code_detail_activity

    private val mArticle by lazy { autoWired<Article>(Constants.KEY_SERIALIZABLE) }

    private val mViewModel: CodeDetailViewModel by viewModel() {
        parametersOf(mArticle, """${mArticle?.user?.nickname ?: "佚名"}
                                        |${mArticle?.pubDate}""".trimMargin())
    }

    override fun loadData(isRefresh: Boolean) {

        mViewModel.loadData().bindLifeCycle(this).subscribe({ isStow(it) },
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
        mBinding.vm = mViewModel
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
            R.id.fab -> mViewModel.stow().bindLifeCycle(this)
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