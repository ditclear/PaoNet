package com.ditclear.paonet.view.code

import android.support.transition.Slide
import android.support.v4.widget.NestedScrollView
import android.view.View
import androidx.navigation.Navigation
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.CodeDetailActivityBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.getCompactColor
import com.ditclear.paonet.helper.extens.toast
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.code.viewmodel.CodeDetailViewModel

/**
 * 页面描述：ArticleDetailFragment
 *
 * Created by ditclear on 2017/10/1.
 */
class CodeDetailFragment : BaseFragment<CodeDetailActivityBinding>() {


    override fun getLayoutId(): Int = R.layout.code_detail_activity

    private val viewModel by lazy {
        getInjectViewModel<CodeDetailViewModel>()
    }
    private val mArticle by lazy { autoWired<Article>(Constants.KEY_SERIALIZABLE) }

    override fun loadData(isRefresh:Boolean) {

        viewModel.loadData().bindLifeCycle(this).subscribe({ isStow(it) },
                { toastFailure(it) })
    }

    fun isStow(stow: Boolean?) {
        activity?.let {
            mBinding.fab.drawable.setTint(
                    if (stow == true) {
                        it.getCompactColor(R.color.stow_color)
                    } else {
                        it.getCompactColor(R.color.tools_color)
                    })
        }
    }


    override fun initView() {
        inList = false
        val slide= Slide()
        enterTransition = slide
        exitTransition = slide
        allowEnterTransitionOverlap=false
        allowReturnTransitionOverlap=false

        if (mArticle == null) {
            activity?.let {
                it.toast("文章不存在", ToastType.WARNING)
                Navigation.findNavController(it,R.id.nav_host).navigateUp()

            }
        }
        getComponent().inject(this)

        mBinding.run {
            vm = viewModel.apply {
                this.article.set(mArticle)
                this.nameAndDate.set("""${mArticle?.user?.nickname ?: "佚名"}
                                        |${mArticle?.pubDate}""".trimMargin())
            }
            scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY - oldScrollY > 10) {
                    mBinding.fab.hide()
                } else if (scrollY - oldScrollY < -10) {
                    mBinding.fab.show()
                }
            })
        }
        mBinding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
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