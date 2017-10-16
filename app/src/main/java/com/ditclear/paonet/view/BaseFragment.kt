package com.ditclear.paonet.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ditclear.paonet.di.component.FragmentComponent
import com.ditclear.paonet.di.module.FragmentModule
import com.ditclear.paonet.vendor.recyclerview.LoadMoreRecyclerView
import com.trello.rxlifecycle2.components.support.RxFragment


/**
 * 页面描述：fragment 基类
 *
 * Created by ditclear on 2017/9/27.
 */
abstract class BaseFragment< VB : ViewDataBinding> : RxFragment() {

    protected lateinit var mBinding: VB

    protected lateinit var mContext: Context

    protected var lazyLoad = false

    protected var visible = false


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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false)
        mContext = activity
        initView()
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
            visible = true
            onVisible()
        } else {
            visible = false
            onInvisible()
        }
    }

    protected fun onInvisible() {

    }

    protected fun onVisible() {
        lazyLoad()
    }


    fun lazyLoad() {}

    abstract fun loadData(isRefresh: Boolean)

    abstract fun initArgs(savedInstanceState: Bundle?)

    abstract fun initView()

    abstract fun getLayoutId(): Int

    fun toast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }


    protected fun initRecyclerView(view: LoadMoreRecyclerView?, loadMore: ObservableField<Boolean>) {
        view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView!!.layoutManager is LinearLayoutManager) {
                    //表示是否能向上滚动，false表示已经滚动到底部

                    if (!recyclerView.canScrollVertically(1)) {
                        if (loadMore.get()) {
                            loadData(false)
                            //防止多次拉取同样的数据
                            loadMore.set(false)
                        }
                    }
                }
            }
        })
    }

}