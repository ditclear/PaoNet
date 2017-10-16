package com.ditclear.paonet.view.auth.viewmodel

import android.databinding.Bindable
import com.ditclear.paonet.BR
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.lib.extention.getOriginData
import com.ditclear.paonet.model.data.BaseResponse
import com.ditclear.paonet.model.remote.api.UserService
import com.ditclear.paonet.viewmodel.BaseViewModel
import com.ditclear.paonet.viewmodel.callback.ICallBack
import java.util.regex.Pattern
import javax.inject.Inject


/**
 * 页面描述：LoginViewModel
 *
 * Created by ditclear on 2017/10/10.
 */
class LoginViewModel @Inject
constructor(private val repo: UserService) : BaseViewModel() {

    private val PASSWORD_PATTERN = "^[a-zA-Z0-9_]{6,16}$"
    private val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"

    private val emailPattern = Pattern.compile(EMAIL_PATTERN)
    private val passwordPattern = Pattern.compile(PASSWORD_PATTERN)


    var passwordError = ""
        @Bindable get
        set(value) = notifyPropertyChanged(BR.passwordError)
    var emailError = ""
        @Bindable get
        set(value) = notifyPropertyChanged(BR.emailError)

    var email = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }
    var password = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    @Bindable("emailError")
    fun isEmailInValid() = emailError.isNotEmpty()

    @Bindable("passwordError")
    fun isPasswordInValid() = passwordError.isNotEmpty()

    @Bindable("password", "email")
    fun isBtnEnabled(): Boolean {
        return !(!emailPattern.matcher(email).matches()
                || !passwordPattern.matcher(password).matches())
    }

    fun attemptToSignIn() {
        clearError()
        repo.login(email, password)
                .getOriginData()
                .compose(bindToLifecycle())
                .async(800)
                .subscribe({ t: BaseResponse? ->t?.let { mView.onLoginSuccess(t) } }
                        , { t: Throwable? -> t?.let { mView.toastFailure(t) }})

    }

    private fun clearError() {
        emailError = ""
        passwordError = ""
    }

    private lateinit var mView: CallBack

    fun attachView(v: CallBack) {
        this.mView = v
    }


    interface CallBack : ICallBack {

        fun onLoginSuccess(response: BaseResponse)

    }

}