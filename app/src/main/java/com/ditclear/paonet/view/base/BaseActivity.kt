package com.ditclear.paonet.view.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.transition.TransitionListenerAdapter
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.ditclear.paonet.PaoApp
import com.ditclear.paonet.di.component.ActivityComponent
import com.ditclear.paonet.di.module.ActivityModule
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.dispatchFailure
import com.ditclear.paonet.helper.extens.toast
import com.ditclear.paonet.helper.presenter.Presenter
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/27.
 */
abstract class BaseActivity<VB : ViewDataBinding> : RxAppCompatActivity(), Presenter {

    protected lateinit var mBinding: VB

    protected lateinit var mContext: Context

    private var activityComponent: ActivityComponent? = null

    protected var autoRefresh =true
    protected var delayToTransition =false


    @NonNull
    fun getComponent(): ActivityComponent {
        if (activityComponent == null) {
            val mainApplication = application as PaoApp
            activityComponent = mainApplication.component.plus(ActivityModule(this))
        }
        return activityComponent as ActivityComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<VB>(this, getLayoutId())
        mBinding.setLifecycleOwner(this)
        mContext = this

        initView()
        if (delayToTransition){
            afterEnterTransition()
        } else if (autoRefresh){
            loadData()
        }


    }

    private fun afterEnterTransition(){
        window.enterTransition.addListener(object : TransitionListenerAdapter(), android.transition.Transition.TransitionListener {
            override fun onTransitionResume(transition: android.transition.Transition?) {

            }

            override fun onTransitionPause(transition: android.transition.Transition?) {
            }

            override fun onTransitionCancel(transition: android.transition.Transition?) {
            }

            override fun onTransitionStart(transition: android.transition.Transition?) {
            }

            override fun onTransitionEnd(transition: android.transition.Transition?) {
                loadData()
            }

        })
    }

    abstract fun loadData()

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
}