package com.ditclear.paonet.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/27.
 */
abstract class BaseActivity <VB:ViewDataBinding>: RxAppCompatActivity() {

    protected lateinit var mBinding :VB

    protected lateinit var mContext :Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView<VB>(this,getLayoutId())

        mContext=this

        initView()

        loadData()
    }

    abstract fun loadData()

    abstract fun initView()

    abstract fun getLayoutId(): Int

    fun toast(msg : String){ Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show()}
}