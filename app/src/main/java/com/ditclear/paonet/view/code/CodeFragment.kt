package com.ditclear.paonet.view.code

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.HomeFragmentBinding
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.lib.adapter.viewpager.AbstractPagerAdapter
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.MainActivity
import com.ditclear.paonet.view.helper.CodeType

/**
 * 页面描述：代码
 *
 * Created by ditclear on 2017/9/30.
 */
@FragmentScope
class CodeFragment : BaseFragment<HomeFragmentBinding>(){


    lateinit var pagerAdapter: FragmentStatePagerAdapter

    companion object {

        fun newInstance()= CodeFragment()
    }
    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun loadData(isRefresh: Boolean) {

    }
    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        pagerAdapter = object : AbstractPagerAdapter(childFragmentManager,
                arrayOf("全部", "提示(Tip)","对话框(Dialog)","日历(Calendar)","相机(Camera)","透明指示层(HUD)"
                        ,"图像(Image)","文件管理(File)","异步加载(sync)","地图(Map)","菜单(Menu)","工具条(Toolbar)","选择器(picker)"
                        ,"进度条(ProgressBar)","滚动视图(ScrollView)","分段选择(Segment)","滑杆(Slider)","网格(GridView)","开关(Switch)","选项卡(Tab Bar)"
                        ,"列表(ListView)","文字输入框(EditText)","文本显示(TextView)","网页(WebView)","动画(Animation)","音频声效(Audio)","图表(Chart)"
                        ,"游戏引擎(Game)","重力感应(CoreMotion)","数据库(DataBase)","绘图(Canvas)","电子书(EBook)","手势交互(Gesture)","引导页(Guide)","网络(NetWork)"
                        ,"弹出视图(Popup View)","社交分享(Socialization)","视图效果(View Effects)","视图布局(View Layouts)","视图切换(View Transition)","其它(Others)")) {
            override fun getItem(pos: Int): Fragment? {
                if (list[pos]==null) {
                    when (pos) {
                        0 -> list[pos] = CodeListFragment.newInstance(null)
                        1 -> list[pos] = CodeListFragment.newInstance(CodeType.TIP)
                        2 -> list[pos] = CodeListFragment.newInstance(CodeType.DIALOG)
                        3 -> list[pos] = CodeListFragment.newInstance(CodeType.CALENDAR)
                        4 -> list[pos] = CodeListFragment.newInstance(CodeType.CAMERA)
                        5 -> list[pos] = CodeListFragment.newInstance(CodeType.HUD)
                        6 -> list[pos] = CodeListFragment.newInstance(CodeType.IMAGE)
                        7 -> list[pos] = CodeListFragment.newInstance(CodeType.FILE)
                        8 -> list[pos] = CodeListFragment.newInstance(CodeType.SYNC)
                        9 -> list[pos] = CodeListFragment.newInstance(CodeType.MAP)
                        10 -> list[pos] = CodeListFragment.newInstance(CodeType.MENU)
                        11 -> list[pos] = CodeListFragment.newInstance(CodeType.TOOLBAR)
                        12 -> list[pos] = CodeListFragment.newInstance(CodeType.PICKER)
                        13 -> list[pos] = CodeListFragment.newInstance(CodeType.PROGRESSBAR)
                        14 -> list[pos] = CodeListFragment.newInstance(CodeType.SCROLLVIEW)
                        15 -> list[pos] = CodeListFragment.newInstance(CodeType.SEGMENT)
                        16 -> list[pos] = CodeListFragment.newInstance(CodeType.SLIDER)
                        17 -> list[pos] = CodeListFragment.newInstance(CodeType.GRIDVIEW)
                        18 -> list[pos] = CodeListFragment.newInstance(CodeType.SWITCH)
                        19 -> list[pos] = CodeListFragment.newInstance(CodeType.TAB_BAR)
                        20 -> list[pos] = CodeListFragment.newInstance(CodeType.LISTVIEW)
                        21 -> list[pos] = CodeListFragment.newInstance(CodeType.EDITTEXT)
                        22 -> list[pos] = CodeListFragment.newInstance(CodeType.TEXTVIEW)
                        23 -> list[pos] = CodeListFragment.newInstance(CodeType.WEBVIEW)
                        24 -> list[pos] = CodeListFragment.newInstance(CodeType.ANIMATION)
                        25 -> list[pos] = CodeListFragment.newInstance(CodeType.AUDIO)
                        26 -> list[pos] = CodeListFragment.newInstance(CodeType.CHART)
                        27 -> list[pos] = CodeListFragment.newInstance(CodeType.GAME)
                        28 -> list[pos] = CodeListFragment.newInstance(CodeType.CORE_MOTION)
                        29 -> list[pos] = CodeListFragment.newInstance(CodeType.DATABASE)
                        30 -> list[pos] = CodeListFragment.newInstance(CodeType.CANVAS)
                        31 -> list[pos] = CodeListFragment.newInstance(CodeType.EBOOK)
                        32 -> list[pos] = CodeListFragment.newInstance(CodeType.GESTURE)
                        33 -> list[pos] = CodeListFragment.newInstance(CodeType.GUIDE_VIEW)
                        34 -> list[pos] = CodeListFragment.newInstance(CodeType.NETWORKING)
                        35 -> list[pos] = CodeListFragment.newInstance(CodeType.POPUP)
                        36 -> list[pos] = CodeListFragment.newInstance(CodeType.SHARE)
                        37 -> list[pos] = CodeListFragment.newInstance(CodeType.VIEW_EFFECT)
                        38 -> list[pos] = CodeListFragment.newInstance(CodeType.VIEW_LAYOUT)
                        39 -> list[pos] = CodeListFragment.newInstance(CodeType.VIEW_TRANSITION)
                        40 -> list[pos] = CodeListFragment.newInstance(CodeType.OTHERS)
                    }
                }
                return list[pos]
            }
        }

        mBinding.viewPager.adapter=pagerAdapter
        (activity as MainActivity).needShowTab(true)
        (activity as MainActivity).setupWithViewPager(mBinding.viewPager)
    }



}