package com.ditclear.paonet.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.transition.TransitionListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.ditclear.paonet.BR
import com.ditclear.paonet.PaoApp
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.dispatchFailure
import com.ditclear.paonet.helper.extens.toast


/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/27.
 */
abstract class BaseActivity< VB : ViewDataBinding> : AppCompatActivity(), Presenter {

    protected val  mBinding: VB by lazy { DataBindingUtil.setContentView<VB>(this, getLayoutId()) }

    protected lateinit var mContext: Context

    protected var autoRefresh = true
    protected var delayToTransition = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.setVariable(BR.presenter, this)
        mBinding.executePendingBindings()
        mBinding.setLifecycleOwner(this)
        mContext = this

        initView()
        if (delayToTransition) {
            afterEnterTransition()
        } else if (autoRefresh) {
            loadData(true)
        }
    }

    private var enterTransitionListener =
            object : TransitionListenerAdapter(), android.transition.Transition.TransitionListener {
                override fun onTransitionResume(transition: android.transition.Transition?) {

                }

                override fun onTransitionPause(transition: android.transition.Transition?) {
                }

                override fun onTransitionCancel(transition: android.transition.Transition?) {
                }

                override fun onTransitionStart(transition: android.transition.Transition?) {
                }

                override fun onTransitionEnd(transition: android.transition.Transition?) {
                    loadData(true)
                }

            }


    private fun afterEnterTransition() {
        window.enterTransition.addListener(enterTransitionListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        window.enterTransition.removeListener(enterTransitionListener)
    }

    abstract override fun loadData(isRefresh: Boolean)

    abstract fun initView()

    abstract fun getLayoutId(): Int


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun initBackToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        val bar = supportActionBar
        if (bar != null) {
            bar.title = null
            bar.setDisplayHomeAsUpEnabled(true)
            bar.setDisplayShowHomeEnabled(true)
            bar.setDisplayShowTitleEnabled(true)
            bar.setHomeButtonEnabled(true)
        }
    }

    fun toastSuccess(msg: String?) {
        msg?.let { toast(it, ToastType.SUCCESS) }
    }

    fun toastFailure(error: Throwable?) {
        dispatchFailure(error)
    }

    override fun onClick(v: View?) {

    }

    protected fun <T> autoWired(key: String, default: T? = null): T? {
        return intent?.extras?.let { findWired(it, key, default) }
    }

    private fun <T> findWired(bundle: Bundle, key: String, default: T? = null): T? {
        return if (bundle.get(key) != null) {
            try {
                bundle.get(key) as T
            } catch (e: ClassCastException) {
                e.printStackTrace()
                null
            }
        } else default

    }
}