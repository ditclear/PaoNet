package com.ditclear.paonet.view.article

import android.support.v4.widget.NestedScrollView
import android.view.View
import androidx.navigation.Navigation
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.ArticleDetailActivityBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.SystemBarHelper
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.getCompactColor
import com.ditclear.paonet.helper.extens.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.article.viewmodel.ArticleDetailViewModel
import com.ditclear.paonet.view.base.BaseFragment


/**
 * 页面描述：ArticleDetailFragment
 *
 * Created by ditclear on 2017/10/1.
 */
class ArticleDetailFragment : BaseFragment<ArticleDetailActivityBinding>() {


    override fun getLayoutId(): Int = R.layout.article_detail_activity

    private val viewModel: ArticleDetailViewModel by lazy { getInjectViewModel<ArticleDetailViewModel>() }

    private val mArticle by lazy { autoWired<Article>(Constants.KEY_SERIALIZABLE) }

    override fun loadData(isRefresh: Boolean) {

        viewModel.loadData().bindLifeCycle(this)
                .subscribe({ t: Boolean? -> t?.run { isStow(t) } },
                        { toastFailure(it) })

    }


    //是否收藏过
    private fun isStow(stow: Boolean) {
        activity?.let {
            mBinding.fab.drawable.setTint(
                    if (stow) {
                        it.getCompactColor(R.color.stow_color)
                    } else {
                        it.getCompactColor(R.color.tools_color)
                    })
        }
    }

    override fun initView() {
        inList = false
        activity?.let {
            it.window?.statusBarColor = it.getCompactColor(android.R.color.transparent)
            SystemBarHelper.setHeightAndPadding(it, mBinding.toolbar)
        }

        if (mArticle == null) {
            activity?.let {
                it.toast("文章不存在", ToastType.WARNING)
                Navigation.findNavController(it, R.id.nav_host).navigateUp()
            }
        }

        setHasOptionsMenu(true)

        getComponent().inject(this)

        mBinding.vm = viewModel.apply {
            mArticle?.let {
                this.article = it
            }
        }
        mBinding.toolbar.apply {
            inflateMenu(R.menu.menu_attention)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.action_attention) {
                    onClick(mBinding.toolbar.findViewById(it.itemId))
                }
                return@setOnMenuItemClickListener true
            }
        }.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        mBinding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY - oldScrollY > 10) {
                mBinding.fab.hide()
            } else if (scrollY - oldScrollY < -10) {
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


    @CheckLogin
    @SingleClick
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab -> stow()
            R.id.action_attention -> attentionTo()
        }
    }

    private fun attentionTo() {
        viewModel.attentionTo().bindLifeCycle(this)
                .subscribe({}, { toastFailure(it) })
    }

    private fun stow() {
        viewModel.stow().bindLifeCycle(this)
                .subscribe({ toastSuccess(it.message) }
                        , { toastFailure(it) })
    }

}