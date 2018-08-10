package com.ditclear.paonet.view.article

import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.ArticleDetailActivityBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.getCompactColor
import com.ditclear.paonet.helper.extens.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.article.viewmodel.ArticleDetailViewModel
import com.ditclear.paonet.view.base.BaseActivity
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

        val article: Article? = intent?.extras?.getSerializable(Constants.KEY_SERIALIZABLE) as Article?
        if (article == null) {
            toast("文章不存在", ToastType.WARNING)
            finish()
        }

        getComponent().inject(this)

        mBinding.vm = viewModel.apply {
            article?.let {
                this.article=it
            }
        }
        mBinding.presenter = this

        initBackToolbar(mBinding.toolbar)

        delayToTransition =true

        ViewCompat.setTransitionName(mBinding.thumbnailIv,Constants.VIEW_NAME_IMAGE)
        ViewCompat.setTransitionName(mBinding.toolbar,Constants.VIEW_NAME_TITLE)
        window.enterTransition.excludeChildren(window.decorView.rootView.findViewById<View>(android.R.id.statusBarBackground),true)

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
        viewModel.attentionTo().compose(bindToLifecycle())
                .subscribe({},{toastFailure(it)})
    }

    private fun stow() {
        viewModel.stow().compose(bindToLifecycle())
                .subscribe({toastSuccess(it.message) }
                        , {  toastFailure(it) })
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

}