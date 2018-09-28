package com.ditclear.paonet.view.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ditclear.paonet.BR
import com.ditclear.paonet.di.component.FragmentComponent
import com.ditclear.paonet.di.module.FragmentModule
import com.ditclear.paonet.helper.annotation.ToastType
import com.ditclear.paonet.helper.extens.dispatchFailure
import com.ditclear.paonet.helper.extens.toast
import javax.inject.Inject


/**
 * 页面描述：fragment 基类
 *
 * Created by ditclear on 2017/9/27.
 */

abstract class BaseFragment<VB : ViewDataBinding> : Fragment(), Presenter {

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

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var fragmentComponent: FragmentComponent? = null
    protected lateinit var fragmentComponentBuilder: FragmentComponent.Builder
    @NonNull
    fun getComponent(): FragmentComponent {
        if (fragmentComponent != null) {
            return fragmentComponent as FragmentComponent
        }

        val activity = activity
        if (activity is BaseActivity<*>) {
            fragmentComponentBuilder = activity.getComponent().supplyFragmentComponentBuilder()
            fragmentComponent = fragmentComponentBuilder.fragmentModule(FragmentModule(this)).build()
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

    inline fun <reified T: ViewModel> getInjectViewModel(): T =
            ViewModelProviders.of(this, factory).get(T::class.java)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity ?: throw Exception("activity 为null")
        retainInstance = true
        initView()
        if (lazyLoad) {
            //延迟加载，需重写lazyLoad方法
            lazyLoad()
        } else {
            // 加载数据
            loadData(true);
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false)
        mBinding.setVariable(BR.presenter, this)
        mBinding.executePendingBindings()
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

    protected open fun onVisible() {
        lazyLoad()
    }


    open fun lazyLoad() {}

    open fun initArgs(savedInstanceState: Bundle?) {

    }

    abstract fun initView()
    abstract override fun loadData(isRefresh: Boolean)

    abstract fun getLayoutId(): Int

    fun toast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastSuccess(msg: String?) {
        msg?.let { activity?.toast(it, ToastType.SUCCESS) }
    }

    fun toastFailure(error: Throwable) {
        activity?.dispatchFailure(error)
    }

    override fun onClick(v: View?) {

    }

    protected fun <T> autoWired(key: String, default: T? = null): T? =
            arguments?.let { findWired(it, key, default) }

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