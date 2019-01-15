package com.ditclear.paonet.view.article

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.ArticleDetailFragmentBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.init
import com.ditclear.paonet.view.article.viewmodel.ArticleDetailViewModel
import com.ditclear.paonet.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/**
 * 页面描述：ArticleDetailFragment
 *
 * Created by ditclear on 2017/10/1.
 */
class ArticleDetailFragment : BaseFragment<ArticleDetailFragmentBinding>() {


    override fun getLayoutId(): Int = R.layout.article_detail_fragment


    private val articleId by lazy { autoWired<Int>(Constants.KEY_DATA) }

    private val mViewModel by viewModel<ArticleDetailViewModel> { parametersOf(articleId) }


    companion object {

        fun newInstance(id: Int) = ArticleDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(Constants.KEY_DATA, id)
            }
        }
    }

    override fun loadData(isRefresh: Boolean) {

        mViewModel.loadData().bindLifeCycle(this)
                .subscribe({ t: Boolean? -> t?.run { isStow(t) } },
                        { toastFailure(it) })

    }


    //是否收藏过
    private fun isStow(stow: Boolean) {
    }

    override fun initView() {

        mBinding.vm = mViewModel

        mBinding.webView.init()
    }


    fun observeArticle() = mViewModel.article


    override fun onDestroy() {

        mBinding.webView.clearHistory()
        mBinding.webView.onPause()
        mBinding.webView.destroy()
        (mBinding.webView.rootView as ViewGroup).removeAllViews()
        System.gc();
        super.onDestroy()
    }


    @CheckLogin
    @SingleClick
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab -> stow()
            R.id.action_attention -> attentionTo()
        }
    }

    private fun attentionTo() {
        mViewModel.attentionTo().bindLifeCycle(this)
                .subscribe({}, { toastFailure(it) })
    }

    private fun stow() {
        mViewModel.stow().bindLifeCycle(this)
                .subscribe({ toastSuccess(it.message) }
                        , { toastFailure(it) })
    }

}
