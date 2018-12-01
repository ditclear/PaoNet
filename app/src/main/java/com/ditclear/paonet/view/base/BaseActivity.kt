package com.ditclear.paonet.view.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.transition.TransitionListenerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
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

    protected lateinit var mBinding: VB

    protected lateinit var mContext: Context

    protected var autoRefresh = true
    protected var delayToTransition = false

    val factory: ViewModelProvider.Factory by lazy {
        if (application is PaoApp) {
            val mainApplication = application as PaoApp
            return@lazy mainApplication.factory
        } else {
            throw IllegalStateException("application is not PaoApp")
        }
    }

    protected inline fun <reified T:ViewModel> getInjectViewModel() = ViewModelProviders.of(this, factory).get(T::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<VB>(this, getLayoutId())
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