package com.ditclear.paonet.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.ditclear.paonet.PaoApp
import com.ditclear.paonet.di.component.ActivityComponent
import com.ditclear.paonet.di.module.ActivityModule
import com.ditclear.paonet.lib.extention.ToastType
import com.ditclear.paonet.lib.extention.toast
import com.ditclear.paonet.viewmodel.callback.ICallBack
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/27.
 */
abstract class BaseActivity<VB : ViewDataBinding> : RxAppCompatActivity(),ICallBack {

    protected lateinit var mBinding: VB

    protected lateinit var mContext: Context

    private var activityComponent: ActivityComponent? = null

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

        mContext = this

        initView()

        loadData()
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

    override fun toastSuccess(msg: String?) {
        msg?.let { toast(msg,ToastType.SUCCESS) }
    }

    override fun toastFailure(error: Throwable) {
        error.message?.let { it -> toast(it,ToastType.ERROR) }
    }
}