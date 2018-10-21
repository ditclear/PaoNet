package com.ditclear.paonet.view.auth

import android.view.View
import androidx.navigation.Navigation
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.LoginActivityBinding
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.view.auth.viewmodel.LoginViewModel
import com.ditclear.paonet.view.base.BaseFragment
import javax.inject.Inject

/**
 * 页面描述：LoginFragment
 *
 * Created by ditclear on 2017/10/10.
 */
class LoginFragment : BaseFragment<LoginActivityBinding>() {

    @Inject
    lateinit var viewModel: LoginViewModel

    private fun onBack() {
        activity?.let {
            Navigation.findNavController(it, R.id.nav_host).navigateUp()
        }
    }

    override fun loadData(isRefresh: Boolean) {
    }

    override fun initView() {
        getComponent().inject(this)
        mBinding.run {
            vm = viewModel.apply {
                showLogin.set(SpUtil.user == null)
                showLogout.set(SpUtil.user != null)
            }
        }

    }

    override fun getLayoutId(): Int = R.layout.login_activity


    @SingleClick
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_btn -> viewModel.attemptToLogIn().bindLifeCycle(this)
                    .subscribe({
                            SpUtil.initUser(it)
                            onBack()

                    }, {
                        toastFailure(it) })

            R.id.logout_btn -> viewModel.attemptToLogout().bindLifeCycle(this)
                    .subscribe({
                        SpUtil.logout()
                        onBack()
                    }, {
                        toastFailure(it)
                    })
        }

    }


}