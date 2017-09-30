package com.ditclear.paonet.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * 页面描述：fragment 基类
 *
 * Created by ditclear on 2017/9/27.
 */
abstract class BaseFragment <VB : ViewDataBinding>:RxFragment() {

    protected lateinit var mBinding :VB

    protected lateinit var mContext :Context

    protected var lazyLoad =false

    protected var visible =false


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding=DataBindingUtil.inflate(inflater,getLayoutId(),null,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext=activity
        initArgs()
        initView()
        if (lazyLoad) {
            //延迟加载，需重写lazyLoad方法
            lazyLoad();
        } else {
            // 加载数据
            loadData(true);
        }
    }

    /**
     * 是否可见，延迟加载
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            visible=true
            onVisible()
        } else {
            visible=false
            onInvisible()
        }
    }

    protected fun onInvisible() {

    }

    protected fun onVisible() {
        lazyLoad()
    }


    fun lazyLoad(){}

    abstract fun loadData(isRefresh: Boolean)

    abstract fun initArgs()

    abstract fun initView()

    abstract fun getLayoutId():Int

    fun toast(msg : String){ Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show()}

}