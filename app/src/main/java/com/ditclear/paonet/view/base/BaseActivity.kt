package com.ditclear.paonet.view.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.transition.TransitionListenerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.ditclear.paonet.BR
import com.ditclear.paonet.PaoApp
import com.ditclear.paonet.di.component.ActivityComponent
import com.ditclear.paonet.di.module.ActivityModule
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.dispatchFailure
import com.ditclear.paonet.helper.extens.toast
import javax.inject.Inject


/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/27.
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(), Presenter {

    protected lateinit var mBinding: VB

    protected lateinit var mContext: Context

    private var activityComponent: ActivityComponent? = null

    protected var autoRefresh =true
    protected var delayToTransition =false

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @NonNull
    fun getComponent(): ActivityComponent {
        if (activityComponent == null) {
            val mainApplication = application as PaoApp
            activityComponent = mainApplication.component.plus(ActivityModule(this))
        }
        return activityComponent as ActivityComponent
    }

    inline fun <reified T :ViewModel> getInjectViewModel ()= ViewModelProviders.of(this,factory).get(T::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<VB>(this, getLayoutId())
        mBinding.setVariable(BR.presenter,this)
        mBinding.executePendingBindings()
        mBinding.setLifecycleOwner(this)
        mContext = this

        initView()
        if (delayToTransition){
            afterEnterTransition()
        } else if (autoRefresh){
            loadData(true)
        }


    }

    private val enterTransitionListener by lazy {
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
    }

    private fun afterEnterTransition(){
        window.enterTransition.addListener(enterTransitionListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        window.enterTransition.removeListener(enterTransitionListener)
    }

    abstract override fun loadData(isRefresh:Boolean)

    abstract fun initView()

    abstract fun getLayoutId(): Int



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
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