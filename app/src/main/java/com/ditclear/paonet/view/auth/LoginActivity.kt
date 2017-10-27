package com.ditclear.paonet.view.auth

import android.support.v4.content.ContextCompat
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.LoginActivityBinding
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.auth.viewmodel.LoginViewModel
import com.ditclear.paonet.view.helper.SpUtil
import com.ditclear.paonet.view.transitions.FabTransform
import com.ditclear.paonet.view.transitions.MorphTransform
import javax.inject.Inject

/**
 * 页面描述：LoginActivity
 *
 * Created by ditclear on 2017/10/10.
 */
class LoginActivity : BaseActivity<LoginActivityBinding>() {

    @Inject
    lateinit var viewModel: LoginViewModel

    //login success
    fun onLoginSuccess(user: User?) {

        user?.let { SpUtil.user = user }
        dismiss(null)
    }

    override fun loadData() {
    }

    override fun initView() {
        getComponent().inject(this)
        mBinding.run {
            vm = viewModel
            presenter=this@LoginActivity
        }

        if (!FabTransform.setup(this, mBinding.container)) {
            MorphTransform.setup(this, mBinding.container,
                    ContextCompat.getColor(this, R.color.background_light),
                    resources.getDimensionPixelSize(R.dimen.cardview_default_radius))
        }
    }

    override fun getLayoutId(): Int = R.layout.login_activity

    fun dismiss(v: View?) {
        mBinding.formLayout.visibility = View.INVISIBLE
        finishAfterTransition()
    }

    override fun onBackPressed() {
        dismiss(null)
    }

    @SingleClick
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> viewModel.attemptToLogIn().compose(bindToLifecycle())
                    .subscribe({ t: User? -> t.let { onLoginSuccess(it) } }
                            , { t: Throwable? -> t?.let { toastFailure(it) } })
            R.id.super_container -> dismiss(v)
        }

    }


}