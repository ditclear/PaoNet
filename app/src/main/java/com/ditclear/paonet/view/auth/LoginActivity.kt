package com.ditclear.paonet.view.auth

import androidx.core.content.ContextCompat
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.LoginActivityBinding
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.set
import com.ditclear.paonet.helper.transitions.FabTransform
import com.ditclear.paonet.helper.transitions.MorphTransform
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.view.auth.viewmodel.LoginViewModel
import com.ditclear.paonet.view.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 页面描述：LoginActivity
 *
 * Created by ditclear on 2017/10/10.
 */
class LoginActivity : BaseActivity<LoginActivityBinding>() {

    private val mViewModel :LoginViewModel by viewModel()

    //login success
    fun onLoginSuccess(user: User?) {

        user?.let { SpUtil.user = user }
        dismiss(null)
    }

    override fun loadData(isRefresh:Boolean) {
    }

    override fun initView() {

        mBinding.vm=mViewModel.apply {
            showLogin .set(SpUtil.user == null)
            showLogout.set(SpUtil.user!=null)
        }

        if (!FabTransform.setup(this, mBinding.container)) {
            MorphTransform.setup(this, mBinding.container,
                    ContextCompat.getColor(this, R.color.background_light),
                    resources.getDimensionPixelSize(R.dimen.cardview_default_radius))
        }
    }

    override fun getLayoutId(): Int = R.layout.login_activity

    private fun dismiss(v: View?) {
        mBinding.formLayout.visibility = View.INVISIBLE
        finishAfterTransition()
    }

    override fun onBackPressed() {
        dismiss(null)
    }

    @SingleClick
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_btn -> mViewModel.attemptToLogIn().bindLifeCycle(this)
                    .subscribe({onLoginSuccess(it)  }
                            , { toastFailure(it)  })

            R.id.logout_btn -> mViewModel.attemptToLogout().bindLifeCycle(this)
                    .subscribe({
                        SpUtil.logout()
                        dismiss(v)
                    },{
                        toastFailure(it)
                    })
            R.id.super_container -> dismiss(v)
        }

    }


}