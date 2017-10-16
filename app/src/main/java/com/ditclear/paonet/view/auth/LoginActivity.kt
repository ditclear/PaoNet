package com.ditclear.paonet.view.auth

import android.content.Intent
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.LoginActivityBinding
import com.ditclear.paonet.model.data.BaseResponse
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.MainActivity
import com.ditclear.paonet.view.auth.viewmodel.LoginViewModel
import com.trello.rxlifecycle2.android.ActivityEvent
import javax.inject.Inject

/**
 * 页面描述：LoginActivity
 *
 * Created by ditclear on 2017/10/10.
 */
class LoginActivity :BaseActivity<LoginActivityBinding>(),LoginViewModel.CallBack{

    @Inject
    lateinit var viewModel:LoginViewModel

    override fun onLoginSuccess(response: BaseResponse) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    override fun loadData() {
    }

    override fun initView() {
        getComponent().inject(this)
        viewModel.attachView(this)
        viewModel.lifecycle=bindToLifecycle<ActivityEvent>()
        mBinding.vm=viewModel
    }

    override fun getLayoutId(): Int= R.layout.login_activity

}