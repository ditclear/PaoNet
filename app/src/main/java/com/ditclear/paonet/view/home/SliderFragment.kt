package com.ditclear.paonet.view.home

import android.os.Bundle
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.SliderFragmentBinding
import com.ditclear.paonet.view.BaseFragment

/**
 * 页面描述：SliderFragment
 *
 * Created by ditclear on 2017/10/23.
 */
class SliderFragment : BaseFragment<SliderFragmentBinding>() {

    val thumbnail: String by lazy { arguments.getString(KEY_THUMBNAIL) }

    val title: String by lazy { arguments.getString(KEY_TITLE) }

    companion object {
        val KEY_THUMBNAIL="thumbnail"
        val KEY_TITLE="title"
        fun newInstance(thumbnail: String?,title:String?):SliderFragment {
            val bundle=Bundle()
            bundle.putString(KEY_THUMBNAIL,thumbnail)
            bundle.putString(KEY_TITLE,title)
            val fragment= SliderFragment()
            fragment.arguments=bundle
            return fragment
        }
    }

    override fun loadData(isRefresh: Boolean) {
    }

    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        mBinding.thumbnail=thumbnail
        mBinding.title=title
    }

    override fun getLayoutId() = R.layout.slider_fragment

}