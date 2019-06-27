package com.ditclear.paonet.view.article

import androidx.core.widget.NestedScrollView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.ArticleDetailActivityBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.argument
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.getCompactColor
import com.ditclear.paonet.helper.extens.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.article.viewmodel.ArticleDetailViewModel
import com.ditclear.paonet.view.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/**
 * 页面描述：ArticleDetailActivity
 *
 * Created by ditclear on 2017/10/1.
 */
class ArticleDetailActivity : BaseActivity<ArticleDetailActivityBinding>() {


    override fun getLayoutId(): Int = R.layout.article_detail_activity

    private val mArticle by argument<Article>(Constants.KEY_SERIALIZABLE)

    private val mViewModel by viewModel<ArticleDetailViewModel> { parametersOf(mArticle) }

    override fun loadData(isRefresh: Boolean) {

        mViewModel.loadData().bindLifeCycle(this)
                .subscribe({ t: Boolean? -> t?.run { isStow(t) } },
                        { toastFailure(it) })

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

        mBinding.vm = mViewModel

        initBackToolbar(mBinding.toolbar)

        delayToTransition = true

        mBinding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY - oldScrollY > 10) {
                mBinding.fab.hide()
            } else if (scrollY - oldScrollY < -10) {
                mBinding.fab.show()
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_attention, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_attention -> onClick(mBinding.toolbar.findViewById(item.itemId))
        }
        return super.onOptionsItemSelected(item)
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
        mViewModel.attentionTo().bindLifeCycle(this)
                .subscribe({}, { toastFailure(it) })
    }

    private fun stow() {
        mViewModel.stow().bindLifeCycle(this)
                .subscribe({ toastSuccess(it.message) }
                        , { toastFailure(it) })
    }

}