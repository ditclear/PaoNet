package com.ditclear.paonet.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ditclear.paonet.di.component.FragmentComponent
import com.ditclear.paonet.di.module.FragmentModule
import com.ditclear.paonet.lib.extention.ToastType
import com.ditclear.paonet.lib.extention.toast
import com.trello.rxlifecycle2.components.support.RxFragment


/**
 * 页面描述：fragment 基类
 *
 * Created by ditclear on 2017/9/27.
 */
abstract class BaseFragment< VB : ViewDataBinding> : RxFragment(), View.OnClickListener {

    protected lateinit var mBinding: VB

    protected lateinit var mContext: Context

    protected var lazyLoad = false

    protected var visible = false

    /**
     * 标志位，标志已经初始化完成
     */
    protected var isPrepared: Boolean = false
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    protected var hasLoadOnce: Boolean = false


    private var fragmentComponent: FragmentComponent? = null

    @NonNull
    fun getComponent(): FragmentComponent {
        if (fragmentComponent != null) {
            return fragmentComponent as FragmentComponent
        }

        val activity = activity
        if (activity is BaseActivity<*>) {
            fragmentComponent = activity.getComponent().plus(FragmentModule(this))
            return fragmentComponent as FragmentComponent
        } else {
            throw IllegalStateException(
                    "The activity of this fragment is not an instance of BaseActivity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity
        initView()
        if (lazyLoad) {
            //延迟加载，需重写lazyLoad方法
            lazyLoad();
        } else {
            // 加载数据
            loadData(true);
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false)
        return mBinding.root
    }

    /**
     * 是否可见，延迟加载
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            visible = true
            onVisible()
        } else {
            visible = false
            onInvisible()
        }
    }

    protected fun onInvisible() {

    }

    open protected fun onVisible() {
        lazyLoad()
    }


    open fun lazyLoad() {}


    abstract fun initArgs(savedInstanceState: Bundle?)

    abstract fun initView()
    abstract fun loadData(isRefresh:Boolean)

    abstract fun getLayoutId(): Int

    fun toast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastSuccess(msg: String?) {
        msg?.let { activity.toast(it, ToastType.SUCCESS) }
    }

    fun toastFailure(error: Throwable) {
        error.message?.let { activity.toast(it,ToastType.ERROR) }
    }

    override fun onClick(v: View?) {
    }

}